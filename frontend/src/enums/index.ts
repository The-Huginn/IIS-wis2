export enum LoginType
{
    PUBLIC,
    ADMIN,
    STUDENT,
    LECTOR
}

export enum CourseRoleStatus
{
    LECTOR = "Lektor",
    GUARANT = "Garant",
    BOTH = "Lektor + Garant"
}

export enum RouteHandles
{
    LOGIN = "/login",
    LOGOUT = "/logout",
    DASHBOARD = "/dashboard",
    COURSE_MANAGER_PUBLIC = "/course-manager",
    // STUDENT ROUTES
    COURSE_MANAGER_STUDENT = "/dashboard/course-manager-student",
    COURSE_MANAGER_VIEW_STUDENT = "/dashboard/course-manager-student/view",
    // LECTOR ROUTES
    COURSE_MANAGER_LECTOR = "/dashboard/course-manager-lector",
    COURSE_MANAGER_VIEW_LECTOR = "/dashboard/course-manager-lector/view-lector",
    COURSE_MANAGER_VIEW_GUARANT = "/dashboard/course-manager-lector/view-guarant",
    COURSE_MANAGER_VIEW_DATE_LECTOR = "/dashboard/course-manager-lector/view-lector/date-view",
    COURSE_MANAGER_EVALUATE = "/dashboard/course-manager-lector/view-lector/date-view/evaluate",
    COURSE_MANAGER_ADD_DATE = "/dashboard/course-manager-lector/view-guarant/date-add",
    COURSE_MANAGER_EDIT_DATE = "/dashboard/course-manager-lector/view-guarant/date-edit",
    // ADMIN ROUTES
    LECTOR_MANAGER = "/dashboard/lector-manager",
    LECTOR_MANAGER_ADD = "/dashboard/lector-manager/add",
    STUDENT_MANAGER = "/dashboard/student-manager",
    STUDENT_MANAGER_ADD = "/dashboard/student-manager/add",
    ROOM_MANAGER = "/dashboard/room-manager",
    ROOM_MANAGER_ADD = "/dashboard/room-manager/add",
    COURSE_MANAGER = "/dashboard/course-manager",
    COURSE_MANAGER_ADD = "/dashboard/course-manager/add",
}