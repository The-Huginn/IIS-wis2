export interface Room {
    id: number
    code: string
    description: string
}

export interface Lector {
    id: number
    name: string
    surname: string
    username: string
}

export interface Student {
    id: number
    name: string
    surname: string
    username: string
}

export interface Evaluation
{
    id: number
    student: Student
    evaluation: number
}

export interface Date
{
    id: number
    room: Room
    description: string
    date: string
    time: string
}

export interface Course {
    id: number
    code: string
    name: string
    description: string
    guarant: {
        id: number
        name: string
        surname: string
        username: string
    }
}

export interface CourseFull {
    course: Course,
    dates: Array<Date>
    lectors: Array<Lector>
    students: Array<Student>
    studentsWithRegistration: Array<Student>
}

export interface CourseDate {
    courseDate: Date
    evaluations: Array<Evaluation>
}

export interface MyCourseDate {
    courseDate: Date
    eval: number
}