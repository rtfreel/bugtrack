import type { User } from "../types/User";

function createAuthHeader() {
    const userJson = localStorage.getItem("user");
    if (!userJson) {
        return {};
    }
    let user = JSON.parse(userJson) as User;
    if (user && user.token) {
      	return { 
			Authorization: "Bearer " + user.token
		};
    } else {
      	return {};
    }
}

export const authHeader = createAuthHeader();
