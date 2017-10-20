"""This module defines custom error types for Watnotes."""


class InvalidAttribute(Exception):
    """Error for an invalid attribute on a model."""

    def __init__(self, attribute, value):
        self.attribute = attribute
        self.value = value

    def __str__(self):
        return "Invalid value '{}' for attribute '{}'".format(
            self.value, self.attribute)
