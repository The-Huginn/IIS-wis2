import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { CourseRoleStatus } from "@root/enums"
import { AuthContext, ErrorContext, Message } from "@root/exports"
import { Course, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Card, Form, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const CourseManagerStudent = () => {
    const [courses, setCourses] = useState<Array<Course>>([])
    const [myCourses, setMyCourses] = useState<Array<Course & { state: CourseRoleStatus }>>([])
    const [showMyCourses, setShowMyCourses] = useState<boolean>(true)
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)

    const errorContext = useContext(ErrorContext)
    const { value } = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (value)
        {
            Promise.all([
                value.getAllCoursesLector().then(courses => setCourses(courses)),
                value.getMyCoursesLector().then(courses => setMyCourses(courses)),
            ]).catch(() => errorContext.setValue(true)).finally(() => setLoading(false)).finally(() => setLoaded(true))
        }
    }, [])

    useEffect(() => {
        if (loading)
        {
            setLoading(false)
        }
    }, [loading, courses])

    const Courses = () => {
        if (loading || !loaded)
        {
            return <Loading/>
        }

        if (showMyCourses)
        {
            if (!myCourses.length)
            {
                return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný váš kurz.</p>
            }
    
            return <Table className="text-center">
                <thead>
                    <tr>
                        <th>Kód kurzu</th>
                        <th>Jméno kurzu</th>
                        <th>Status</th>
                        <th>Akce</th>
                    </tr>
                </thead>
                <tbody className="align-middle">
                    {myCourses.map(course => <tr key={course.id}>
                        <td>{course.code}</td>
                        <td>{course.name}</td>
                        <td>{course.state}</td>
                        <td className="d-flex justify-content-center">
                            <Link to={course.state !== CourseRoleStatus.LECTOR ? "view-guarant" : "view-lector"} state={{ courseId: course.id }} className="no-link">
                                <Button variant="warning" className="d-flex align-items-center">
                                    <i className="bi bi-info-circle me-1"></i>
                                    <span>Zobrazit detail</span>
                                </Button>
                            </Link>
                        </td>
                    </tr>)}
                </tbody>
            </Table>
        }

        if (!courses.length)
        {
            return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný dostupný kurz.</p>
        }

        return <Table className="text-center">
            <thead>
                <tr>
                    <th>Kód kurzu</th>
                    <th>Jméno kurzu</th>
                    <th>Garant kurzu</th>
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

    return <ContentLayout title="Správa kurzů">
        <div className="p-3 pb-0">
            <Form.Check 
                type="checkbox"
                id="my-courses"
                label="Zobrazit pouze moje kurzy"
                checked={showMyCourses}
                onChange={(e) => setShowMyCourses(e.target.checked)}
            />
        </div>
        <hr className="mb-0"/>
        <Courses/>
    </ContentLayout>
}

export default CourseManagerStudent