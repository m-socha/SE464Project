#!/bin/bash

set -eufo pipefail

die() {
    echo "Error: $*" >&2
    exit 1
}

say() {
    echo " * $*"
}

macos() {
    say "Installing dependencies on macOS"
    if ! command -v brew > /dev/null; then
        die "Command brew not found (install it from https://brew.sh)"
    fi

    for p in python3 postgresql elasticsearch; do
        if brew list --versions "$p" > /dev/null; then
            say "Note: $p already installed"
        elif ! brew install "$p"; then
            die "Failed to install $p"
        fi
    done
}

osname=$(uname -s)
if [[ "$osname" == 'Darwin' ]]; then
    macos
elif [[ "$osname" == 'Linux' ]]; then
    die "Linux not supported yet"
else
    die "OS '$osname' not supported"
fi

say "Installing Python packages..."
if ! pip3 install -r requirements.txt; then
    die "Failed to install requirements with pip3"
fi

say "Successfully installed all dependencies"
