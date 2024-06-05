export function escapeDescription(description: string) {
    let tmpDiv = document.createElement("div");
    tmpDiv.innerText = description;
    return tmpDiv.innerHTML.replace("\n", "<br />");
}

export function formatDate(date: Date | undefined): string {
    if (!date) return "";
    const pad = (num: number): string => (num < 10 ? '0' + num : num.toString());

    const day = pad(date.getDay());
    const month = pad(date.getMonth() + 1);
    const year = date.getFullYear();
    const hours = pad(date.getHours());
    const minutes = pad(date.getMinutes());

    return `${day}.${month}.${year} ${hours}:${minutes}`;
}
