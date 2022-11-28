import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { AuthContext, Message } from "@root/exports"
import { Course, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Card, Form, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const CourseManagerStudent = () => {
    const [courses, setCourses] = useState<Array<Course>>([])
    const [myCourses, setMyCourses] = useState<Array<Course>>([])
    const [showMyCourses, setShowMyCourses] = useState<boolean>(true)
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)
    const [error, setError] = useState<boolean>(false)

    const { value } = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (value)
        {
            Promise.all([
                value.getAllCoursesStudent().then(courses => setCourses(courses)),
                value.getMyCoursesStudent().then(courses => setMyCourses(courses)),
            ]).catch(() => setError(true)).finally(() => (setLoading(false), setLoaded(true)))
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

        let renderCourses = []

        if (showMyCourses)
        {
            if (!myCourses.length)
            {
                return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný váš kurz.</p>
            }
    
            renderCourses = myCourses
        }
        else
        {
            if (!courses.length)
            {
                return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný dostupný kurz.</p>
            }
    
            renderCourses = courses
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
                {renderCourses.map(course => <tr key={course.id}>
                    <td>{course.code}</td>
                    <td>{course.name}</td>
                    <td className="d-flex justify-content-center">
                        <Link to={"view"} state={{ courseId: course.id }} className="no-link">
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