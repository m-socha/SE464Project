#!/bin/bash

set -eufo pipefail

pg_dir=data
pg_log=postgres.log
init_db=init.sql

cmd=
verbose=0

die() {
    echo "Error: $*" >&2
    exit 1
}

say() {
    echo "* $*"
}

usage()  {
    echo "Usage: $0 [-v] COMMAND ..."
    echo
    echo "Commands:"
    echo "  create  (Re)create the database"
    echo "  drop    Delete the database"
    echo "  start   Start postgres"
    echo "  stop    Stop postgres"
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
    run pg_ctl -D "$pg_dir" status
}

create_db() {
    drop_db

    say "Creating a postgres database cluster in $pg_dir"
    if ! run initdb "$pg_dir"; then
        die "initdb failed"
    fi

    start_db

    if ! [[ -f "$init_db" ]]; then
        die "Could not find $init_db"
    fi

    say "Running $init_db"
    if ! run psql -d postgres -f "$init_db"; then
        die "Failed to initialize database"
    fi

    stop_db
}

drop_db() {
    if pg_running; then
        stop_db
    fi

    say "Deleting database directory"
    rm -rf "$pg_dir"
}

start_db() {
    if pg_running; then
        say "Note: postgres is already running (stop it with $0 stop)"
        return
    fi

    if ! [[ -d "$pg_dir" ]]; then
        die "Database has not been created (create it with $0 create)"
    fi

    say "Starting postgres (logs in $pg_log)"
    if ! run pg_ctl -D "$pg_dir" -w -l "$pg_log" start; then
        die "Failed to start postgres"
    fi
}

stop_db() {
    if ! pg_running; then
        say "Note: postgres is not running (start it with $0 start)"
        return
    fi

    say "Stopping postgres"
    if ! run pg_ctl -D "$pg_dir" stop; then
        die "Failed to stop postgres"
    fi
}

parse_args() {
    while (( $# )); do
        case "$1" in
            -h|--help) usage 0 ;;
            -v|--verbose) verbose=1 ;;
            create|drop|start|stop) cmd=$1 ;;
            *) usage 1 ;;
        esac
        shift
    done
}

main() {
    parse_args "$@"
    case "$cmd" in
        create) create_db ;;
        drop) drop_db ;;
        start) start_db ;;
        stop) stop_db ;;
        *) usage 1 ;;
    esac
}

main "$@"
