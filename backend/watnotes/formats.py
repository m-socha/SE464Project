"""This module defines valid formats and relationships between them."""


def extension_to_mime(extension: str) -> str:
    """Convert a file extension to a MIME type, or return None."""
    MAP = {
        'txt': 'text/plain',
        'text': 'text/plain',
        'png': 'image/png',
        'jpg': 'image/jpeg',
        'jpeg': 'image/jpeg'
    }

    return MAP.get(extension.lower())


def mime_to_extension(mime: str) -> str:
    """Convert a MIME type to a file extension, or return None."""
    MAP = {
        'text/plain': 'txt',
        'image/png': 'png',
        'image/jpeg': 'jpg'
    }

    return MAP.get(mime.lower())


def is_valid_mime(mime: str) -> bool:
    return mime_to_extension(mime) is not None
