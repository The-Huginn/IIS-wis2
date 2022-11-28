import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { AuthContext, Message } from "@root/exports"
import { Course, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Card, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const CourseManager = () => {
    const [courses, setCourses] = useState<Array<Course>>([])
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)
    const [error, setError] = useState<boolean>(false)

    const { value } = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (value)
        {
            value.getAllCourses().then(courses => setCourses(courses)).finally(() => setLoading(false)).finally(() => setLoaded(true))
        }
    }, [])

    useEffect(() => {
        if (loading)
        {
            setLoading(false)
        }
    }, [loading, courses])

    const deleteCourse = (courseId: number) => {
        if (!value)
        {
            return
        }

        setLoaded(false)
        setLoading(true)

        value.deleteCourse(courseId).then(() => value.getAllCourses().then(courses => setCourses(courses))).catch(e => {
            setError(true)
            console.error(e)
        }).finally(() => setLoaded(true))
    }

    const Rooms = () => {
        if (loading || !loaded)
        {
            return <Loading/>
        }

        if (!courses.length)
        {
            return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný kurz.</p>
        }

        return <Table className="text-center">
            <thead>
                <tr>
                    <th>Kód kurzu</th>
                    <th>Jméno kurzu</th>
                    <th>Akce</th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {courses.map(course => <tr key={course.id}>
                    <td>{course.code}</td>
                    <td>{course.name}</td>
                    <td className="d-flex justify-content-center">
                        <Button onClick={() => deleteCourse(course.id)} variant="danger" className="d-flex align-items-center">
                            <i className="bi bi-x-lg me-1"></i>
                            <span>Smazat</span>
                        </Button>
                    </td>
                </tr>)}
            </tbody>
        </Table>
    }

    return <ContentLayout title="Správa kurzů">
        <Link to="add">
            <Button className="mt-3 me-3 ms-auto d-block">Přidat kurz</Button>
        </Link>
        <hr className="mb-0"/>
        <Rooms/>
    </ContentLayout>
}

export default CourseManager