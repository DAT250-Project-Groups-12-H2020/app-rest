function isOpen(result){
    let start = new Date(result.startDateTime);
    let now = new Date(Date.now());
    return result.endDateTime == null || start <= now && result.endDateTime >= now;
}