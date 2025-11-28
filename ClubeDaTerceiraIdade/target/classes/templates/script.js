const body = document.querySelector("body"),
    modeToggle  = body.querySelector(".mode-toggle");
    sidebar = body.querySelector("nav");
    sidebarToggle = body.querySelector(".sidebar-toggle");

modeToggle.addEventListener("click", (e) => {
    body.classList.toggle("dark");
})

sidebarToggle.addEventListener("click", (e) => {
    sidebar.classList.toggle("close");
})