import axios from "axios";
import type { Bug } from "../types/Bug";
import { authHeader } from "../../auth/utils/header";
import { Status } from "../types/Status";

const API_ENDPOINT = import.meta.env.API_URL + "/bug/";

export class BugService {
    async getReportedBugs(statuses: Status[], query: string | undefined): Promise<Bug[]> {
        const url = API_ENDPOINT + "list/reported";
        const response = await axios
            .post(url, {
                query: query || "",
                statuses: statuses || [Status.ACTIVE, Status.NEW, Status.CLOSED, Status.TESTING]
            }, { headers: authHeader });
        return response.data as Bug[];
    }

    async getProjectBugs(projectId: number, statuses: Status[], query: string | undefined): Promise<Bug[]> {
        const url = API_ENDPOINT + "list/project=" + projectId + "/all";
        const response = await axios
            .post(url, {
                query: query || "",
                statuses: statuses || [Status.ACTIVE, Status.NEW, Status.CLOSED, Status.TESTING]
            }, { headers: authHeader });
        return response.data as Bug[];
    }

    async getBug(id: number): Promise<Bug> {
        const url = API_ENDPOINT + id;
        const response = await axios
            .get(url, { headers: authHeader });
        return response.data as Bug;
    }

    async saveBug(bug: Bug): Promise<Bug> {
        const url = API_ENDPOINT + bug.id;
        const response = await axios
            .put(url, bug, { headers: authHeader });
        return response.data as Bug;
    }
}
