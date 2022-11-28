import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { AuthContext, Message } from "@root/exports"
import { Course, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Card, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const CourseManagerPublic = () => {
    const [courses, setCourses] = useState<Array<Course>>([])
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)
    const [error, setError] = useState<boolean>(false)

    useEffect(() => {
        fetch(API.apiUrl + "/public/course").then(res => res.json()).then(data => setCourses(data)).finally(() => setLoading(false)).finally(() => setLoaded(true))
    }, [])

    useEffect(() => {
        if (loading)
        {
            setLoading(false)
        }
    }, [loading, courses])

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
                    <th>Jméno garanta</th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {courses.map(course => <tr key={course.id}>
                    <td>{course.code}</td>
                    <td>{course.name}</td>
                    <td>{course.guarant.name} {course.guarant.surname}</td>
                </tr>)}
            </tbody>
        </Table>
    }

    return <ContentLayout goBack title="Správa kurzů">
        <Rooms/>
    </ContentLayout>
}

export default CourseManagerPublic