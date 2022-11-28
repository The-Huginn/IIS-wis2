import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { AuthContext, Message } from "@root/exports"
import { Lector, Student } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Card, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const StudentManager = () => {
    const [students, setStudents] = useState<Array<Student>>([])
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(true)
    const [error, setError] = useState<boolean>(false)

    const { value } = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (value)
        {
            value.getAllStudents().then(students => setStudents(students)).finally(() => setLoaded(true))
        }
    }, [])

    useEffect(() => {
        if (loading)
        {
            setLoading(false)
        }
    }, [students, loading])

    const deleteStudent = (studentId: number) => {
        if (!value)
        {
            return
        }

        setLoading(true)

        value.deleteStudent(studentId).then(() => {
            return value.getAllStudents().then(lectors => setStudents(lectors))
        }).catch(e => {
            setError(true)
            console.error(e)
        })
    }

    const Students = () => {
        if (loading || !loaded)
        {
            return <Loading/>
        }

        if (!students.length)
        {
            return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný student.</p>
        }

        return <Table striped responsive className="text-center">
            <thead>
                <tr>
                    <th>Uživatelské jméno</th>
                    <th>Jméno a příjmení</th>
                    <th>Akce</th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {students.map(student => <tr key={student.id}>
                    <td>{student.username}</td>
                    <td>{student.name} {student.surname}</td>
                    <td className="d-flex justify-content-center">
                        <Button onClick={() => deleteStudent(student.id)} variant="danger" className="d-flex align-items-center">
                            <i className="bi bi-x-lg me-1"></i>
                            <span>Smazat</span>
                        </Button>
                    </td>
                </tr>)}
            </tbody>
        </Table>
    }

    return <ContentLayout title="Správa studentů">
        <Link to="add">
            <Button className="mt-3 me-3 ms-auto d-block">Přidat studenta</Button>
        </Link>
        <hr className="mb-0"/>
        <Students/>
    </ContentLayout>
}

export default StudentManager