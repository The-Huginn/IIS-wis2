import { Message } from "@root/exports"
import { Spinner } from "react-bootstrap"

const Loading = () => {
    return <div className="text-center mt-3">
        <Spinner/>
        <p>{Message.LOADING}</p>
    </div>
}

export default Loading