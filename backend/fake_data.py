"""This script generates fake data in the Watnotes databse."""

from faker import Faker
from faker.providers import BaseProvider

from watnotes.database import db
from watnotes.models import *


class MyProvider(BaseProvider):
    """Faker provider for Watnotes-specific fields."""

    DEPARTMENTS = ['MATH', 'CS', 'SE', 'ECE', 'SCI', 'ENGL', 'STV']

    TITLE_PREFIXES = [
        "Introduction to", "Crash Course in", "Elementary", "Principles of",
        "Foundations of", "Readings in", "Applications of", "Topics in",
        "Distributed", "Real-Time", "Computational", "Social", "Numerical"
    ]
    TITLE_SUBJECTS = [
        "Linear Algebra", "Mechanics", "Electromagnetics", "Programming",
        "Compiler Construction", "Geology", "Software Architecture",
        "Logic and Computation", "Aviation", "Economics", "Calculus"
    ]
    TITLE_SUFFIXES = [
        "", "1", "2", "for Engineers", "for Honours Mathematics",
        "for Professionals", "With Applications", "and Society"
    ]

    def course_code(self):
        return "{} {}".format(
            self.random_element(self.DEPARTMENTS),
            self.random_number(digits=3)
        )

    def course_title(self):
        return "{} {} {}".format(
            self.random_element(self.TITLE_PREFIXES),
            self.random_element(self.TITLE_SUBJECTS),
            self.random_element(self.TITLE_SUFFIXES)
        ).strip()


fake = Faker()
fake.add_provider(MyProvider)

NUM_USERS = 30
NUM_COURSES = 10
NUM_NOTEBOOKS = 20
NUM_NOTES = 200
NOTE_MAX_CHARS = 300

MAX_ITERATIONS = 100


def say(message):
    print(" * {}".format(message))


def get_unique(gen, used):
    i = 0
    while True:
        obj = gen()
        if obj not in used:
            used.add(obj)
            return obj

        i += 1
        if i > MAX_ITERATIONS:
            raise Exception("Too many iterations")


def gen_users():
    users = []
    used = set()
    for i in range(NUM_USERS):
        email = get_unique(fake.email, used)
        u = User(email=email, name=fake.name())
        users.append(u)
        db.session.add(u)

    db.session.commit()
    say("Added {} users".format(len(users)))
    return users


def gen_courses():
    courses = []
    used = set()
    for i in range(NUM_COURSES):
        code = get_unique(fake.course_code, used)
        c = Course(code=code, title=fake.course_title())
        courses.append(c)
        db.session.add(c)

    db.session.commit()
    say("Added {} courses".format(len(courses)))
    return courses


def gen_notebooks(users, courses):
    notebooks = []
    used = set()
    for i in range(NUM_NOTEBOOKS):
        gen = lambda: (fake.random_element(users).id,
                       fake.random_element(courses).id)
        user_id, course_id = get_unique(gen, used)
        nb = Notebook(user_id=user_id, course_id=course_id)
        notebooks.append(nb)
        db.session.add(nb)

    db.session.commit()
    say("Added {} notebooks".format(len(notebooks)))
    return notebooks


def gen_notes(notebooks):
    notes = []
    for i in range(NUM_NOTES):
        n = Note(
            notebook_id=fake.random_element(notebooks).id,
            index=fake.random_int(),
            format='text',
            data=bytes(fake.text(max_nb_chars=NOTE_MAX_CHARS), 'utf-8')
        )
        notes.append(n)
        db.session.add(n)
    db.session.commit()
    say("Added {} notes".format(len(notes)))
    return notes


def main():
    users = gen_users()
    courses = gen_courses()
    notebooks = gen_notebooks(users, courses)
    notes = gen_notes(notebooks)
    say("Successfully generated fake data")


main()
