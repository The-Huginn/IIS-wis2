import { useState } from "react"
import { Link, Routes, Route } from "react-router-dom"
import { Col, Row, Button } from "react-bootstrap"
import classNames from "classnames"

import Homepage from "@root/pages/Homepage"
import Logout from "@root/pages/Logout"
import Login from "@root/pages/Login"

import ProtectedRoute from "@root/components/ProtectedRoute"
import ProtectedAdminRoute from "@root/components/ProtectedAdminRoute"
import ProtectedStudentRoute from "@root/components/ProtectedStudentRoute"
import ProtectedLectorRoute from "@root/components/ProtectedLectorRoute"
import ExpiryTimer from "@root/components/ExpiryTimer"
import Menu from "@root/components/Menu"
import { LoginType, RouteHandles } from "@root/enums"

import { AuthContext, AuthContextValueType } from "@root/exports"

import StudentManager from "./pages/Dashboard/Admin/StudentManager/StudentManager"
import StudentManagerAdd from "./pages/Dashboard/Admin/StudentManager/StudentManagerAdd"
import LectorManager from "./pages/Dashboard/Admin/LectorManager/LectorManager"
import LectorManagerAdd from "./pages/Dashboard/Admin/LectorManager/LectorManagerAdd"
import RoomManager from "./pages/Dashboard/Admin/RoomManager/RoomManager"
import RoomManagerAdd from "./pages/Dashboard/Admin/RoomManager/RoomManagerAdd"
import CourseManager from "./pages/Dashboard/Admin/CourseManager/CourseManager"
import CourseManagerAdd from "./pages/Dashboard/Admin/CourseManager/CourseManagerAdd"

import CourseManagerStudent from "./pages/Dashboard/Student/CourseManager/CourseManagerStudent"

import CourseManagerLector from "./pages/Dashboard/Lector/CourseManager/CourseManagerLector"
import CourseManagerViewGuarant from "./pages/Dashboard/Lector/CourseManager/CourseManagerViewGuarant"
import CourseManagerAddDate from "./pages/Dashboard/Lector/CourseManager/CourseManagerAddDate"
import CourseManagerViewStudent from "./pages/Dashboard/Student/CourseManager/CourseManagerViewStudent"
import CourseManagerViewLector from "./pages/Dashboard/Lector/CourseManager/CourseManagerViewLector"
import CourseManagerPublic from "./pages/CourseManagerPublic"
import CourseManagerEditDate from "./pages/Dashboard/Lector/CourseManager/CourseManagerEditDate"
import CourseManagerViewDateLector from "./pages/Dashboard/Lector/CourseManager/CourseManagerViewDateLector"
import CourseManagerEvaluateLector from "./pages/Dashboard/Lector/CourseManager/CourseManagerEvaluateLector"

const App = () => {
    const [auth, setAuth] = useState<AuthContextValueType>(null)

    return <AuthContext.Provider value={{
        value: auth,
        setValue: setAuth
    }}>
        <header className="text-white bg-primary w-100">
            <Row className="mx-0">
                <Col xs={6} sm={10} className="p-3">
                    <div className="d-flex">
                        <Link className="d-flex align-items-center no-link" to={"/"}>
                            <img src="https://wis.fit.vutbr.cz/images/fitnewz.png" alt="logo"/>
                            <span className="d-none d-sm-inline h2 ms-2 mb-0">Informační systém</span>
                        </Link>
                    </div>
                </Col>
                <Col xs={6} sm={2} className="d-flex align-items-center justify-content-end">
                    {auth && <>
                        <Button className="d-block d-md-none">
                            <i className="bi bi-list"></i>
                        </Button>
                        <Link to="/logout">
                            <Button className="d-flex align-items-center ms-2">
                                <ExpiryTimer/>
                                <i className="bi bi-box-arrow-right ps-2"></i>
                            </Button>
                        </Link>
                    </>}
                </Col>
            </Row>
        </header>
        <main className="container-fluid bg-light">
            <Row className="h-100">
                <Col className={classNames("p-0 text-center d-none", { "d-md-block": auth })} md={4} lg={3} xl={2}>
                    <Menu/>
                </Col>
                <Col className="p-0" md={auth ? 8 : 12} lg={auth ? 9 : 12} xl={auth ? 10 : 12}>
                    <div className="bg-white h-100 p-5">
                        <Routes>
                            <Route index path="/" element={<Homepage/>}/>
                            <Route path={RouteHandles.LOGIN} element={<Login/>}/>
                            <Route path={RouteHandles.LOGOUT} element={<Logout/>}/>
                            <Route path={RouteHandles.DASHBOARD} element={
                                <ProtectedRoute>
                                    
                                </ProtectedRoute>
                            }/>
                            <Route path={RouteHandles.STUDENT_MANAGER} element={
                                <ProtectedAdminRoute>
                                    <StudentManager/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.STUDENT_MANAGER_ADD} element={
                                <ProtectedAdminRoute>
                                    <StudentManagerAdd/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.LECTOR_MANAGER} element={
                                <ProtectedAdminRoute>
                                    <LectorManager/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.LECTOR_MANAGER_ADD} element={
                                <ProtectedAdminRoute>
                                    <LectorManagerAdd/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.ROOM_MANAGER} element={
                                <ProtectedAdminRoute>
                                    <RoomManager/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.ROOM_MANAGER_ADD} element={
                                <ProtectedAdminRoute>
                                    <RoomManagerAdd/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER} element={
                                <ProtectedAdminRoute>
                                    <CourseManager/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_PUBLIC} element={<CourseManagerPublic/>}/>
                            <Route path={RouteHandles.COURSE_MANAGER_ADD} element={
                                <ProtectedAdminRoute>
                                    <CourseManagerAdd/>
                                </ProtectedAdminRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_STUDENT} element={
                                <ProtectedStudentRoute>
                                    <CourseManagerStudent/>
                                </ProtectedStudentRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_VIEW_STUDENT} element={
                                <ProtectedStudentRoute>
                                    <CourseManagerViewStudent/>
                                </ProtectedStudentRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_LECTOR} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerLector/>
                                </ProtectedLectorRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_VIEW_GUARANT} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerViewGuarant/>
                                </ProtectedLectorRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_VIEW_LECTOR} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerViewLector/>
                                </ProtectedLectorRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_VIEW_DATE_LECTOR} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerViewDateLector/>
                                </ProtectedLectorRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_EVALUATE} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerEvaluateLector/>
                                </ProtectedLectorRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_ADD_DATE} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerAddDate/>
                                </ProtectedLectorRoute>
                            }/>
                            <Route path={RouteHandles.COURSE_MANAGER_EDIT_DATE} element={
                                <ProtectedLectorRoute>
                                    <CourseManagerEditDate/>
                                </ProtectedLectorRoute>
                            }/>
                        </Routes>
                    </div>
                </Col>
            </Row>
        </main>
        <footer className="text-white text-center bg-primary w-100 p-3">
            <span>WIS2 –⁠ IIS Projekt 2022</span>
        </footer>
    </AuthContext.Provider>
}

export default App