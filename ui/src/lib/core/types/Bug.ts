import type { Status } from "./Status"
import type { User } from "./User"

export type Bug = {
    id?: number,
    projectId?: number,
    reporter: User,
    title?: string,
    description?: string,
    status?: Status
}
