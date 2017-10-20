"""This module defines valid formats and relationships between them."""


def convert_mime(mime: str) -> str:
    """Convert a MIME type to a more desirable one for storage."""
    KEEP = ['image/png', 'image/jpeg', 'image/gif']
    if mime.lower() in KEEP:
        return mime
    if mime_is_image(mime):
        return 'image/png'
    return mime


def extension_to_mime(extension: str) -> str:
    """Convert a file extension to a MIME type, or return None."""
    MAP = {
        'txt': 'text/plain',
        'text': 'text/plain',
        'png': 'image/png',
        'jpg': 'image/jpeg',
        'jpeg': 'image/jpeg',
        'gif': 'image/gif'
    }

    return MAP.get(extension.lower())


def mime_to_extension(mime: str) -> str:
    """Convert a MIME type to a file extension, or return None."""
    MAP = {
        'text/plain': 'txt',
        'image/png': 'png',
        'image/jpeg': 'jpg',
        'image/gif': 'gif'
    }

    return MAP.get(mime.lower())


def is_valid_mime(mime: str) -> bool:
    return mime_to_extension(mime) is not None


def mime_is_image(mime: str) -> bool:
    return mime.lower().startswith('image/')
