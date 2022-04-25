# Sponj (Fabric)

![Version: 1.0.1](https://img.shields.io/badge/version-1.0.1-blueviolet?style=flat-square) ![Modloader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=flat-square&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOCAYAAAAfSC3RAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAFGSURBVDhPY8AHpCVF2M2MNQ9CucQDW0u9azvXT/9jbqx5GCpEHABq/H9i37L/uzbMIE6zjJQoI1Dh7aO7F/7397L7f2Db/P+7NhKh2cJE6+62tdP/3b6w5f+xPYv++3rYQDTjshlkE0jTqQPL/t06vxnsTBAN0uznaft/9cLe/0D5v0DNh6BaIADkvJP7loJtAmGQpuN7l/0/unvJ/1ULe/67Opr9L8pJ+A9UdxasAWgTu7WZzt2T+5eCNcBsAuGb5zb/37NpDkjjP6CGb3BNIAC0/vKcqS3/r5xcD9YI03xsz9L/189s/L96Ue8/S1PtayCvgDBUG9iJh9vqc/+sXNiPohlkE0iTmZHGNahSTICu+cbZjWDngWyCKsENYJpXLeoHBwbITyhOwwdAmlvrcv5am+uAQ49ojSAA1HAIpAnKxQIYGABj4eLruqAJiAAAAABJRU5ErkJggg==) ![Client: required](https://img.shields.io/badge/client-required-4caf50?style=flat-square) ![Server: required](https://img.shields.io/badge/server-required-4caf50?style=flat-square)

_Yet another sponge mod_

## Introduction

This simple mod adds two new blocks: a sponj and lava sponj. 

### Versions

* 1.18.2: supported
* 1.17.x: not yet supported
* < 1.17: not planned

### Current Features

* Sponj
  * Absorbs water in a larger radius based on how many sponjes are touching each other
* Lava sponj
  * Absorbs lava instead of water
  * Radius depends on the number of connected lava sponjes, same as the regular sponj
* Wet sponj
  * What you get when a sponj absorbs some water
  * Can be dried out in the nether or smelted to get a sponj back
* Wet lava sponj
  * What you get when a lava sponj absorbs lava
  * Can be dried out in the end
  * (Coming soon) Wet lava sponjes placed in a furnace will behave like a bucket of lava

### Screenshots

**Sponj Recipe**

![Sponj recipe](https://media.forgecdn.net/attachments/456/138/sponj-recipe.png "Sponj recipe graphic")

**Lava Sponj Recipe**

![Lava sponj recipe](https://media.forgecdn.net/attachments/456/139/lava-sponj-recipe.png "Lava sponj recipe graphic")

## Issues & Suggestions

Please use the [GitHub issue tracker](https://github.com/chimericdream/sponj-fabric/issues) to report any bugs you find.

## Credits

Obviously this mod would not be possible if not for the people at Mojang making an awesome game. In addition, many
thanks go to the developers of the Fabric mod loader.

The features in this mod were inspired by similar functionality in mods such as [OpenBlocks](https://www.curseforge.com/minecraft/mc-mods/openblocks)
and [Bigger Sponge Absorption Radius](https://www.curseforge.com/minecraft/mc-mods/bigger-sponge-absorption-radius).
Many thanks to their developers for blazing the trail!

## License

This mod is released under the MIT license. [The full text of the license can be found here.](./LICENSE)
