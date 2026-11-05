# 指令工具 / Funtion Tool

**右键执行写在物品里的命令。**

[English](README.en_us.md) | 中文

指令工具是一个轻量的 Forge 1.20.1 物品模组。物品的 `function` NBT 里写一条命令，右键时以 OP 权限执行；

`texture` NBT 可以把图标换成任意资源图片。常用于任务书、对话和整合包内的交互道具。

## 特性

- **命令物品**：右键执行 `function` NBT 中的命令。
- **OP 权限**：执行时使用权限等级 4，可运行普通玩家无法执行的命令。
- **动态图标**：`texture` 写完整资源路径即可替换物品图标。
- **可堆叠**：默认最大堆叠 64。
- **无前置**：只依赖 Minecraft Forge。

## 使用

给玩家一个执行命令的指令工具：

```mcfunction
give @s funtion_tool:function_tool{function:"say hello"} 1
```

可选：用 `texture` 指定图标：

```mcfunction
give @s funtion_tool:function_tool{function:"say hello",texture:"story_of_forgotten_land:textures/item/echo_stone.png"} 1
```

`function` 里不要带开头的 `/`。命令以权限等级 4 执行，只应交给可信玩家或由整合包控制发放。

## 环境

- Minecraft 1.20.1
- Forge 47.x

客户端和服务端都安装。

## 许可

MIT License。可自由用于整合包、修改和再分发；保留版权声明即可。
