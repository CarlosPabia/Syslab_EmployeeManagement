const API = "/api/employees";
let page = 0, size = 10, last = true, query = "";

const el = id => document.getElementById(id);
const rows = el("rows");
const status = el("status");
const pageInfo = el("pageInfo");

async function apiList() {
  const url = query
    ? `${API}/search?q=${encodeURIComponent(query)}&page=${page}&size=${size}`
    : `${API}?page=${page}&size=${size}`;
  return fetch(url).then(r => r.json());
}

async function apiCreate(payload) {
  return fetch(API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  }).then(async r => {
    if (!r.ok) throw await r.json();
    return r.json();
  });
}

async function apiUpdate(id, payload) {
  return fetch(`${API}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  }).then(async r => {
    if (!r.ok) throw await r.json();
    return r.json();
  });
}

async function apiDelete(id) {
  return fetch(`${API}/${id}`, { method: "DELETE" }).then(r => {
    if (!r.ok && r.status !== 204) throw new Error("Delete failed");
  });
}

function setStatus(msg) { status.textContent = msg; }

function render(data) {
  last = data.last;
  pageInfo.textContent = `Page ${data.number + 1} • ${data.totalElements ?? 0} total`;
  if (!data.content || data.content.length === 0) {
    rows.innerHTML = `<tr><td colspan="6" class="muted">No data</td></tr>`;
    return;
  }
  rows.innerHTML = data.content.map(e => `
    <tr>
      <td>${e.id}</td>
      <td>${e.firstName} ${e.lastName}</td>
      <td>${e.email}</td>
      <td>${e.department ?? ""}</td>
      <td>${e.salary ?? 0}</td>
      <td>
        <button data-id="${e.id}" class="edit">Edit</button>
        <button data-id="${e.id}" class="warn del">Delete</button>
      </td>
    </tr>
  `).join("");
}

async function load() {
  setStatus("Loading…");
  try {
    const data = await apiList();
    render(data);
    setStatus("Ready");
  } catch (e) {
    setStatus("Error loading");
    console.error(e);
  }
}

function readForm() {
  return {
    firstName: el("firstName").value.trim(),
    lastName: el("lastName").value.trim(),
    email: el("email").value.trim(),
    department: el("department").value.trim(),
    salary: Number(el("salary").value || 0),
  };
}

function resetForm() {
  ["firstName","lastName","email","department","salary"].forEach(id => el(id).value = "");
}

document.addEventListener("click", async (ev) => {
  const t = ev.target;
  if (t.id === "next") { if (!last) { page++; load(); } }
  if (t.id === "prev") { if (page > 0) { page--; load(); } }
  if (t.id === "btnCreate") {
    const body = readForm();
    if (!body.firstName || !body.lastName || !body.email) {
      setStatus("First name, last name, and email are required"); return;
    }
    try {
      setStatus("Creating…");
      await apiCreate(body);
      resetForm();
      page = 0;
      await load();
      setStatus("Created");
    } catch (e) {
      console.error(e);
      setStatus(e?.error || "Create failed");
    }
  }
  if (t.id === "btnSearch") {
    query = el("search").value.trim();
    page = 0;
    load();
  }
  if (t.classList.contains("del")) {
    const id = t.getAttribute("data-id");
    if (!confirm("Delete employee #" + id + "?")) return;
    try {
      setStatus("Deleting…");
      await apiDelete(id);
      await load();
      setStatus("Deleted");
    } catch (e) {
      console.error(e);
      setStatus("Delete failed");
    }
  }
  if (t.classList.contains("edit")) {
    const id = t.getAttribute("data-id");
    const row = t.closest("tr").children;
    const curr = {
      firstName: row[1].textContent.split(" ")[0],
      lastName: row[1].textContent.split(" ").slice(1).join(" "),
      email: row[2].textContent,
      department: row[3].textContent,
      salary: Number(row[4].textContent || 0)
    };
    const nf = prompt("First name:", curr.firstName); if (nf === null) return;
    const nl = prompt("Last name:", curr.lastName); if (nl === null) return;
    const em = prompt("Email:", curr.email); if (em === null) return;
    const dp = prompt("Department:", curr.department); if (dp === null) return;
    const sl = Number(prompt("Salary:", String(curr.salary))); if (Number.isNaN(sl)) return;
    try {
      setStatus("Updating…");
      await apiUpdate(id, { firstName: nf, lastName: nl, email: em, department: dp, salary: sl });
      await load();
      setStatus("Updated");
    } catch (e) {
      console.error(e);
      setStatus(e?.error || "Update failed");
    }
  }
});

document.addEventListener("DOMContentLoaded", load);
el("btnReset").addEventListener("click", resetForm);
