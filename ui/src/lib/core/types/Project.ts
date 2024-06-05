import type { User } from "./User";

export type Project = {
    id?: number,
    owner?: User,
    title?: string,
    description?: string,
    editable?: boolean
}

export type ProjectHistory = {
    id?: number,
    user?: User,
    projectId?: number,
    title?: string,
    description?: string,
    changeTime?: number,
    changeDateTime?: Date,
    changedFields?: string[],
    isCreated?: boolean
}
