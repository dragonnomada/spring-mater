import { useEffect, useState } from "react"

function useLogin() {

  const [isLogged, setIsLogged] = useState(false)
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(null)
  const [error, setError] = useState(null)

  return {
    error,
    isLogged,
    user,
    token,
    autoSignIn() {
      const token = localStorage.getItem("token")
      const user = JSON.parse(localStorage.getItem("user") || "null")
      if (token) {
        setToken(token)
        setUser(user)
        setIsLogged(true)
      }
    },
    async signIn(username, password) {
      // TODO: Call to api sign in
      setError(null)
      const response = await fetch("http://localhost:8080/api/login/signIn", {
        method: "post",
        headers: {
          "content-type": "application/json"
        },
        body: JSON.stringify({
          username,
          password
        })
      })

      if (response.ok) {
        const user = await response.json()
        setUser(user)
        localStorage.setItem("user", JSON.stringify(user))
        setToken(user.token)
        localStorage.setItem("token", user.token)
        setIsLogged(true)
      } else {
        const error = await response.text();
        setError(`Error [${response.status}]: ${error || "Invalid sign in"}`)
      }

    },
    async signOut() {
      // TODO: Call to api sign in
      setError(null)
      const response = await fetch(`http://localhost:8080/api/login/signOut?token=${token}`)

      if (response.ok) {
        setUser(null)
        localStorage.removeItem("user")
        setToken(null)
        localStorage.removeItem("token")
        setIsLogged(false)
      } else {
        const error = await response.text();
        setError(`Error [${response.status}]: ${error || "Invalid sign out"}`)
      }

    }
  }

}

function useTodoStore(token) {

  const [todos, setTodos] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)

  return {
    todos,
    isLoading,
    error,
    async getAll() {
      setError(null)
      setIsLoading(true)

      const response = await fetch(`http://localhost:8080/api/todo/all?token=${token}`)

      setIsLoading(false)

      if (response.ok) {
        const todos = await response.json()
        setTodos(todos)
      } else {
        const error = await response.text();
        setError(`Error [${response.status}]: ${error || "Load todos"}`)
      }
    },
    async addTodo(title) {
      setError(null)
      setIsLoading(true)

      const response = await fetch(`http://localhost:8080/api/todo/add?token=${token}`, {
        method: "put",
        headers: {
          "content-type": "application/json"
        },
        body: JSON.stringify({
          title
        })
      })

      setIsLoading(false)

      if (response.ok) {
        this.getAll()
      } else {
        const error = await response.text();
        setError(`Error [${response.status}]: ${error || "Add todo"}`)
      }
    }
  }

}

function Login({ loginStore }) {
  const [username, setUsername] = useState("") 
  const [password, setPassword] = useState("") 

  return (
    <div>
      <h1>Iniciar sesión</h1>
      <div>
        <span>{loginStore.error}</span>
      </div>
      <div>
        <div>
          <label>Username {username ? `(${username})` : ""}</label> <br />
          <input placeholder="Example: pepe" value={username} onChange={e => setUsername(e.target.value)} />
        </div>
        <div>
          <label>Password</label> <br />
          <input placeholder="Example: ****" value={password} onChange={e => setPassword(e.target.value)} />
        </div>
        <div>
          <button onClick={() => {
            loginStore.signIn(username, password)
          }}>Sign In</button>
        </div>
      </div>
    </div>
  )
}

function Home({ loginStore, user }) {
  const [title, setTitle] = useState("")

  const todoStore = useTodoStore(loginStore.token)

  useEffect(() => {
    if (loginStore.token) {
      todoStore.getAll()
    }
  }, [loginStore.token])

  useEffect(() => {
    setTitle("")
  }, [todoStore.todos])

  return (
    <div>
      <div>
        <h1>Welcome, {user.fullname}</h1>
        <img src={user.picture} />
        <button onClick={() => {
          loginStore.signOut()
        }}>Sign Out</button>
      </div>
      <div>
        <span>{loginStore.error}</span>
        <span>{todoStore.error}</span>
      </div>
      <div>
        <div>
          <label>Write somethig to do...</label>
          <input placeholder="Example: Buy eggs" value={title} onChange={e => setTitle(e.target.value)} />
          <button onClick={() => {
            todoStore.addTodo(title)
          }}>Add</button>
        </div>
        <div>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Checked</th>
                <th>Created</th>
                <th>Updated</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {
                todoStore.todos.map(todo => (
                  <tr>
                  <td>{todo.id}</td>
                  <td>{todo.title}</td>
                  <td><input type="checkbox" value={todo.checked} /></td>
                  <td>{todo.created}</td>
                  <td>{todo.updated}</td>
                  <td>
                    <div>
                      <a href="#details">Details</a>
                      <a href="#remove">🚮 Delete</a>
                    </div>
                  </td>
                </tr>
                ))
              }
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}

export default function App() {
  const loginStore = useLogin()

  useEffect(() => {
    loginStore.autoSignIn()
  }, [])
  
  return loginStore.isLogged ? <Home loginStore={loginStore} user={loginStore.user} /> : <Login loginStore={loginStore} />
}
