#!/bin/bash

die() {
    echo "$@" >&2
    exit 1
}

say() {
    echo "* $*"
}

macos() {
    say "Installing dependencies on macOS"
    if ! command -v brew > /dev/null; then
        die "Error: brew not found (install it from https://brew.sh)"
    fi

    for p in python3 postgresql; do
        if brew list --versions "$p" > /dev/null; then
            say "Note: $p already installed"
        elif ! brew install "$p"; then
            die "Error: failed to install $p"
        fi
    done
}

osname=$(uname -s)
if [[ "$osname" == 'Darwin' ]]; then
    macos
elif [[ "$osname" == 'Linux' ]]; then
    die "Sorry, Linux not supported yet"
else
    die "OS '$osname' not supported"
fi

say "Successfully installed all dependencies"
