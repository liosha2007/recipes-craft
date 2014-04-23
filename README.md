recipes-craft
=============

All recipes for MineCraft and mods



## Build

* For development `mvn clean install` or `mvn clean install -Pdevelopment`
* For release `mvn clean install -Prelease`


## Development notes

For load connected object
```
Category category = item.getCategory()
HelperFactory.getHelper.getRolelDAO().refresh(category);
```

Same for collections
```
@ForeignCollectionField(eager = true)
private Collection<Role> roleList;

public addRole(Role value){
    value.setGoal(this);
    HelperFactory.GetHelper().getRoleDAO().create(value);
    roleList.add(value);
}

public void removeRole(Role value){
    roleList.remove(value);
    HelperFactory.GetHelper().getRoleDAO().delete(value);
}

Аргумент в аннотации eager означает, что все объекты из roleList будут получаться из БД вместе с извлечением объекта типа Goal. Обязательной является ссылка на Goal в объекте Role. А так же нужно отдельно сохранять с помощью RoleDAO каждый из объектов Role.
Соответственно в классе Role должна быть аннотация:

@DatabaseField(foreign = true, foreignAutoRefresh = true)
private Goal goal;


Если не ставить eager=true, то будет осуществлена lazy-инициализация, т.е. при запросе объекта Goal объекты соотвествующие коллекции roleList не будут извлечены. Для их извлечения нужно будет произвести их итерацию:

Iterator<Role> iter = state.goal.getRoleList().iterator();
while (iter.hasNext()) {
    Role r = iter.next();
}
```

http://habrahabr.ru/post/143431/