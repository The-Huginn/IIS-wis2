import { ReactNode } from "react"
import { Button, Card } from "react-bootstrap"
import { useNavigate } from "react-router"

const ContentLayout = ({ title, children, goBack }: { title: string, children: ReactNode, goBack?: boolean }) => {
    const navigate = useNavigate()

    return <Card className="h-100">
        <Card.Header className="text-center">
            {goBack && <a href="#" className="d-flex align-items-center float-start" onClick={() => navigate(-1)}>
                <i className="bi bi-arrow-left-square me-1" style={{ fontSize: "19px" }}></i>
                <span>Zpět</span>
            </a>}
            <h4 className="mb-0">{title}</h4>
        </Card.Header>
        <Card.Body className="p-0">
            {children}
        </Card.Body>
    </Card>
}

export default ContentLayout