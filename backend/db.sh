#!/bin/bash

set -eufo pipefail

pg_dir=database
pg_log=postgres.log
db_name=watnotes
config_path=instance/application.cfg

py_init_db=init_db.py
py_gen_fake=fake_data.py

cmd=
verbose=0

die() {
    echo "Error: $*" >&2
    exit 1
}

say() {
    echo " * $*"
}

usage()  {
    echo "Usage: $0 [-v] COMMAND"
    echo
    echo "Commands:"
    echo "  create  (Re)create the database"
    echo "  drop    Delete the database"
    echo "  start   Start postgres"
    echo "  stop    Stop postgres"
    echo "  psql    Start psql session"
    echo "  fake    Generate fake data"
    echo
    echo "Options:"
    echo "  -v      Verbose output"
    echo

    exit "$1"
}

run() {
    if [[ "$verbose" == 1 ]]; then
        "$@"
    else
        "$@" &> /dev/null
    fi
}

pg_running() {
    if run pg_ctl -D "$pg_dir" status; then
        return 0
    fi

    pid=$(pgrep -x postgres)
    if [[ -n "$pid" ]]; then
        die "There is a rogue postgres process (kill it with 'kill $pid')"
    fi

    return 1
}

pg_runaway_pid() {
    return $(pgrep -x postgres)
}

create_db() {
    drop_db

    say "Creating a postgres database cluster in $pg_dir/"
    if ! run initdb "$pg_dir"; then
        die "initdb failed"
    fi

    start_db

    if ! run createdb "$db_name"; then
        die "createdb failed"
    fi

    mkdir -p "$(dirname "$config_path")"
    if [[ -f "$config_path" ]]; then
        say "Note: config file $config_path already exists"
    else
        say "Creating config file $config_path"
        echo -e "DB_USER = '$(whoami)'\nDB_PASSWORD = ''" > "$config_path"
    fi

    say "Running $py_init_db"
    if ! run python3 "$py_init_db"; then
        die "Failed to initialize database"
    fi

    stop_db
}

drop_db() {
    if pg_running; then
        stop_db
    fi

    say "Deleting database directory $pg_dir/"
    rm -rf "$pg_dir"
}

start_db() {
    if pg_running; then
        say "Note: postgres is already running (stop it with '$0 stop')"
        return
    fi

    if ! [[ -d "$pg_dir" ]]; then
        die "Database has not been created (create it with '$0 create')"
    fi

    say "Starting postgres (logs in $pg_log)"
    if ! run pg_ctl -D "$pg_dir" -w -l "$pg_log" start; then
        die "Failed to start postgres (have you run '$0 create'?)"
    fi
}

stop_db() {
    if ! pg_running; then
        say "Note: postgres is not running (start it with '$0 start')"
        return
    fi

    say "Stopping postgres"
    if ! run pg_ctl -D "$pg_dir" stop; then
        die "Failed to stop postgres"
    fi
}

psql_db() {
    if ! pg_running; then
        start_db
    fi

    say "Connecting to database $db_name"
    if ! psql -d "$db_name"; then
        die "Failed to start psql"
    fi
}

fake_db() {
    if ! pg_running; then
        start_db
    fi

    say "Running $py_gen_fake"
    if ! run python3 "$py_gen_fake"; then
        die "Failed to generate fake data"
    fi
}

parse_args() {
    while (( $# )); do
        case "$1" in
            -h|--help) usage 0 ;;
            -v|--verbose) verbose=1 ;;
            create|drop|start|stop|psql|fake)
                [[ -n "$cmd" ]] && usage 1 ;
                cmd=$1 ;;
            *) usage 1 ;;
        esac
        shift
    done
}

main() {
    cd "$(dirname "$0")"

    parse_args "$@"
    case "$cmd" in
        create) create_db ;;
        drop) drop_db ;;
        start) start_db ;;
        stop) stop_db ;;
        psql) psql_db ;;
        fake) fake_db ;;
        *) usage 1 ;;
    esac
}

main "$@"
