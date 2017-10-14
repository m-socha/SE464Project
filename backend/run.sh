#!/bin/bash

set -eufo pipefail

app=watnotes/__init__.py

debug=0
start_db=1
db_opts=()

usage()  {
    echo "Usage: $0 [options]"
    echo
    echo "Options:"
    echo "  -d  Use Flask debug mode"
    echo "  -n  Do not start the database"
    echo "  -v  Verbose output"
    echo

    exit "$1"
}

parse_args() {
    while (( $# )); do
        case "$1" in
            -h|--help) usage 0 ;;
            -d|--debug) debug=1 ;;
            -n|--no-db) start_db=0 ;;
            -v|--verbose) db_opts+=("$1") ;;
            *) usage 1 ;;
        esac
        shift
    done
}

main() {
    cd "$(dirname "$0")"

    parse_args "$@"

    if [[ "$start_db" == 1 ]]; then
        # Incantation to make empty array work with set -u
        database/db.sh "${db_opts[@]+"${db_opts[@]}"}" start
    fi

    FLASK_APP=$app FLASK_DEBUG=$debug flask run
}

main "$@"