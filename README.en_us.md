# Funtion Tool

**Right-click to run a command stored inside the item.**

English | [中文](README.md)

Funtion Tool is a lightweight Forge 1.20.1 item mod. Write a command into the item's `function` NBT tag and right-click to run it with operator permission. The optional `texture` tag replaces the item icon with any resource image. It is useful for quest books, dialog and pack interaction props.

## Features

- **Command item** — right-click runs the command in the `function` NBT tag.
- **Operator permission** — commands run at permission level 4.
- **Dynamic icon** — set `texture` to a full resource path to replace the icon.
- **Stackable** — stacks up to 64 by default.
- **No dependencies** — only Minecraft Forge is required.

## Usage

Give a player a command tool:

```mcfunction
give @s funtion_tool:function_tool{function:"say hello"} 1
```

Optionally set the icon:

```mcfunction
give @s funtion_tool:function_tool{function:"say hello",texture:"story_of_forgotten_land:textures/item/echo_stone.png"} 1
```

Do not include a leading `/` in `function`. Commands run at permission level 4, so only give this item to trusted players or hand it out from a pack-controlled source.

## Requirements

- Minecraft 1.20.1
- Forge 47.x

Install on both client and server.

## License

MIT. Free to use in modpacks, modify and redistribute with the copyright notice.
