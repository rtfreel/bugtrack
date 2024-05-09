import type { User } from "./User";

export type Project = {
    id?: number,
    owner?: User,
    title?: string,
    description?: string,
    editable?: boolean
}
