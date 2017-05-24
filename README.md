# NongLang

Spring + Spring Mvc + Mybatis 搭建 JavaWeb 项目。

# 接受页面数据

使用 **Map properties = request.getParameterMap();**，获取页面的参数。

以键值对的形式存储页面传递过来的数据，key 表示参数名称，value 为参数值，最终返回一个充满数据的 map。

传递参数即传递这个充满数据的 map 即可，具体代码如下所示：

```
public PageData(HttpServletRequest request){
		this.request = request;
		//获取前端页面参数，返回 Map<String,String[]>
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		//先返回Map.Entry的实例集
		Iterator entries = properties.entrySet().iterator();
		//表示Map中的一个实体（一个key-value对）
		Map.Entry entry;
		String name = "";
		String value = "";
		while(entries.hasNext()){
			//获取Map实例
			entry = (Map.Entry) entries.next();
			//Map的key即为参数名称
			name = (String) entry.getKey();
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		map = returnMap;
	}
```

# shiro 完成认证和授权

利用BigInteger对权限进行2的权的和计算

```java
/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * @param rights String型权限编码数组
	 * @return 2的权的和
	 */
public static BigInteger sumRights(String[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(Integer.parseInt(rights[i]));
		}
		return num;
	}
```

根据角色权限获取本权限的菜单列表(递归处理)

```java
/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
			}
		}
		return menuList;
	}
```
