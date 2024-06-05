import type { Status } from "./Status"
import type { Step } from "./Step"
import type { User } from "./User"

export type Bug = {
    id?: number,
    projectId?: number,
    reporter?: User,
    steps?: Step[],
    title?: string,
    description?: string,
    status?: Status,
    editable?: boolean
}

export type BugHistory = {
    id?: number,
    user?: User,
    bugId?: User,
    status?: Step[],
    title?: string,
    description?: string,
    stepsUpdated?: boolean,
    changeTime?: number,
    changeDateTime?: Date,
    changedFields?: string[],
    isCreated?: boolean
}
