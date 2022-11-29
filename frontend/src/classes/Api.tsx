import { CourseRoleStatus, LoginType } from "@root/enums"
import { Course, CourseDate, CourseFull, Lector, MyCourseDate, Room, Student } from "@root/interfaces"
import { Buffer } from "buffer"

export default class API
{
    public static readonly apiUrl = "http://145.239.85.0:8080/wis2/api"
    private authorization: string | null = null
    private username: string

    public role: LoginType

    constructor(username: string, password: string, role: LoginType)
    {
        this.authorization = API.setAuth(username, password)
        this.username = username
        this.role = role
    }

    public static setAuth(username: string, password: string)
    {
        return this.encodeB64(`${username}:${password}`)
    }

    public static encodeB64(payload: string)
    {
        return Buffer.from(unescape(encodeURIComponent(payload))).toString("base64")
    }

    public getUsername()
    {
        return this.username
    }

    private sendRequest<T>(route: string, method: string, data?: Record<string, string | number | object>)
    {
        const headers: Record<string, string> = {
            Authorization: `Basic ${this.authorization}`,
        }

        if (method !== "GET")
        {
            headers["Content-Type"] = "application/json"
        }

        return fetch(API.apiUrl + route, {
            headers,
            method,
            body: data ? JSON.stringify(data) : undefined
        }).then(res => {
            if (!res.ok)
            {
                return res.text().then(text => {
                    throw new Error(text)
                })
            }

            return res
        }).then<T>(res => {
            try {
                if (res.headers.get("content-type") === "application/json")
                    return res.json()
            } catch(e) { console.error("Failed to parse", e) }

            return res.text()
        })
    }
    
    public changeUserinfo(data: { username: string, surname: string, name: string })
    {
        let url = ""

        if (this.role === LoginType.STUDENT)
        {
            url = "/student/update"
        }
        else if (this.role === LoginType.LECTOR)
        {
            url = "/lector/update"
        }

        if (!url)
        {
            return
        }

        return this.sendRequest(url, "PUT", data)
    }

    public getNames()
    {
        return this.sendRequest<{ name: string, surname: string }>("/public/userinfo/names", "GET")
    }

    //////////////////////////////////////// ADMIN ROOM
    public getAllRooms()
    {
        return this.sendRequest<Array<Room>>("/admin/room", "GET")
    }

    public getRoom(id: number)
    {
        return this.sendRequest<Room>("/admin/room/" + id, "GET")
    }

    public createRoom(data: Omit<Room, "id">)
    {
        return this.sendRequest("/admin/room", "POST", data)
    }

    public deleteRoom(id: number)
    {
        return this.sendRequest("/admin/room/" + id, "DELETE")
    }

    //////////////////////////////////////// ADMIN LECTOR
    public getAllLectors()
    {
        return this.sendRequest<Array<Lector>>("/admin/lector", "GET")
    }

    public getLector(id: number)
    {
        return this.sendRequest<Lector>("/admin/lector/" + id, "GET")
    }

    public createLector(data: Omit<Lector, "id"> & { password: string })
    {
        const { password, ...dataWithoutPassword } = data

        return this.sendRequest("/admin/lector?password=" + API.encodeB64(password), "POST", dataWithoutPassword as Omit<Lector, "id">)
    }

    public deleteLector(id: number)
    {
        return this.sendRequest("/admin/lector/" + id, "DELETE")
    }

    //////////////////////////////////////// ADMIN STUDENT
    public getAllStudents()
    {
        return this.sendRequest<Array<Student>>("/admin/student", "GET")
    }

    public getStudent(id: number)
    {
        return this.sendRequest<Student>("/admin/student/" + id, "GET")
    }

    public createStudent(data: Omit<Student, "id"> & { password: string })
    {
        const { password, ...dataWithoutPassword } = data

        return this.sendRequest("/admin/student?password=" + API.encodeB64(password), "POST", dataWithoutPassword as Omit<Student, "id">)
    }

    public deleteStudent(id: number)
    {
        return this.sendRequest("/admin/student/" + id, "DELETE")
    }

    //////////////////////////////////////// ADMIN COURSE
    public getAllCourses()
    {
        return this.sendRequest<Array<Course>>("/admin/course", "GET")
    }

    public createCourse(data: {
        guarant: string, code: string, name: string, description: string
    })
    {
        const { guarant, ...newData } = data

        return this.sendRequest<{
            id: number
        }>("/admin/course", "POST", newData as unknown as  Record<string, string>).then((response) => this.sendRequest("/admin/guarant/" + response.id + "/" + guarant, "POST"))
    }

    public deleteCourse(id: number)
    {
        return this.sendRequest("/admin/course/" + id, "DELETE")
    }

    //////////////////////////////////////// STUDENT COURSE
    public getAllCoursesStudent()
    {
        return this.sendRequest<Array<Course>>("/student/course", "GET")
    }

    public getAllRegisteredCoursesStudent()
    {
        return this.sendRequest<Array<Course>>("/student/course/myRegistrations", "GET")
    }

    public getAllRegisteredCourseDatesStudent(courseId: number)
    {
        return this.sendRequest<Array<MyCourseDate>>("/student/course/myCourseDates/" + courseId, "GET")
    }

    public getMyCoursesStudent()
    {
        return this.sendRequest<Array<Course>>("/student/course/myCourses", "GET")
    }

    public getCourseStudent(id: number)
    {
        return this.sendRequest<CourseFull>("/student/course/" + id, "GET")
    }

    public signUpCourseStudent(courseId: number)
    {
        return this.sendRequest("/student/course/" + courseId, "POST")
    }

    public signUpCourseDateStudent(courseDateId: number)
    {
        return this.sendRequest("/student/dateEvaluation/" + courseDateId, "POST")
    }

    //////////////////////////////////////// LECTOR COURSE
    public getAllLectorsGuarant()
    {
        return this.sendRequest<Array<Lector>>("/guarant/lector", "GET")
    }

    public getAllCoursesLector()
    {
        return this.sendRequest<Array<Course>>("/lector/course", "GET")
    }

    public getCourseLector(id: number)
    {
        return this.sendRequest<CourseFull>("/lector/course/" + id, "GET")
    }

    public getCourseDateLector(id: number)
    {
        return this.sendRequest<CourseDate>("/lector/courseDate/" + id, "GET")
    }

    public getAllRoomsLector()
    {
        return this.sendRequest<Array<Room>>("/guarant/room", "GET")
    }

    public async getMyCoursesLector()
    {
        const lectorCourses = await this.sendRequest<Array<Course>>("/lector/course/myCourses", "GET")

        const guarantCourses = await this.sendRequest<Array<Course>>("/guarant/course/myCourses", "GET").catch(() => [])

        const map = new Map<number, Course & { state: CourseRoleStatus }>()

        lectorCourses.forEach(course => map.set(course.id, { ...course, state: CourseRoleStatus.LECTOR }))
        guarantCourses.forEach(course => {
            if (map.get(course.id))
            {
                return map.set(course.id, { ...course, state: CourseRoleStatus.BOTH })
            }
        
            map.set(course.id, { ...course, state: CourseRoleStatus.GUARANT })
        })

        return Array.from(map.values())
    }

    public addCurseDateToCourse(data: {
        courseId: number, roomId: number, description: string, date: string, time: string
    })
    {
        const { courseId, roomId, ...newData } = data

        return this.sendRequest("/guarant/courseDate/" + courseId + "/" + roomId, "POST", newData as unknown as  Record<string, string>)
    }

    public editCourseDate(data: {
        courseDateId: number, description: string, date: string, time: string
    })
    {
        const { courseDateId, ...newData } = data

        return this.sendRequest("/guarant/courseDate/update/" + courseDateId, "PUT", newData as unknown as Record<string, string>)
    }

    public evaluateCourseDate(data: {
        evaluationId: number,
        evaluation: number
    })
    {
        const { evaluationId, ...newData } = data

        return this.sendRequest("/lector/dateEvaluation/" + evaluationId, "POST", newData as unknown as Record<string, string>)
    }

    public addStudentToCourse(courseId: number, studentId: number)
    {
        return this.sendRequest("/guarant/course/addStudent/" + courseId + "/" + studentId, "POST")
    }

    public addLectorToCourse(courseId: number, lectorId: number)
    {
        return this.sendRequest("/guarant/course/addLector/" + courseId + "/" + lectorId, "POST")
    }

    public removeLectorFromCourse(courseId: number, lectorId: number)
    {
        return this.sendRequest("/guarant/course/removeLector/" + courseId + "/" + lectorId, "DELETE")
    }
}