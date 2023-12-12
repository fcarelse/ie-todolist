# ie-todolist
Basic Todolist website for users' todolists


Use Environment Settings:

Note: May need to duplicate in .env and in intellij run configuration

```
API_PORT=3000
POSTGRES_SERVER=localhost
POSTGRES_PORT=5332
POSTGRES_PATH=todolist
POSTGRES_USERNAME=todolist
POSTGRES_PASSWORD=password
EMAIL_HOST=example.com
EMAIL_PORT=587
EMAIL_USERNAME=username
EMAIL_PASSWORD=password
EMAIL_FROM=no-reply@example.com
```

To Login use endpoint:
localhost:3000/user/login
```json
{
  "email":"bob@todolist.ie",
  "password":"password"
}
```

Can take the token given and use at Bearer token for endpoint:
localhost:3000/rest/todos
