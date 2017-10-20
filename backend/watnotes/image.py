"""This module provides image manipulation tools."""

from io import BytesIO
from typing import Tuple

from PIL import Image

from watnotes import app
from watnotes.formats import convert_mime, mime_to_extension


# Maximum width and height.
MAX_SIZE = app.config['MAX_IMAGE_WIDTH'], app.config['MAX_IMAGE_HEIGHT']


def process_image_data(data: bytes, format: str) -> Tuple[bytes, str]:
    """Process an image, possibly resizing it and converting to a new format."""
    f = BytesIO(data)
    image = Image.open(f)
    image = resize_image(image, MAX_SIZE)

    output = BytesIO()
    format = convert_mime(format)
    image.save(output, format=mime_to_extension(format))
    data = output.getvalue()
    output.close()

    return data, format


def resize_image(image: Image, max_size) -> Image:
    """Resize an image to be within a maximum size, preserving aspect ratio."""
    w, h = image.size
    mw, mh = max_size
    ratio = min(mw / w, mh / h)
    if ratio >= 1:
        return image

    new_size = round(w * ratio), round(h * ratio)
    return image.resize(new_size, Image.ANTIALIAS)
