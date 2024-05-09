import axios from "axios";
import type { Project } from "../types/Project";
import { authHeader } from "../../auth/utils/header";

const API_ENDPOINT = import.meta.env.API_URL + "/project/";

export class ProjectService {
    async getOwnedProjects(query: string | undefined): Promise<Project[]> {
        const url = API_ENDPOINT + "list/user" + (query ? "/" + query : "");
        const response = await axios
            .get(url, { headers: authHeader });
        return response.data as Project[];
    }

    async getProjects(query: string | undefined): Promise<Project[]> {
        const url = API_ENDPOINT + "list/all" + (query ? "/" + query : "");
        const response = await axios
            .get(url, { headers: authHeader });
        return response.data as Project[];
    }

    async getProject(id: number): Promise<Project> {
        const url = API_ENDPOINT + id;
        const response = await axios
            .get(url, { headers: authHeader });
        return response.data as Project;
    }

    async saveProject(project: Project): Promise<Project> {
        const url = API_ENDPOINT + project.id;
        const response = await axios
            .put(url, project, { headers: authHeader });
        return response.data as Project;
    }
}
