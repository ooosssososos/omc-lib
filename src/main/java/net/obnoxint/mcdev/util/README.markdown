# The net.obnoxint.mcdev.util package

* [JavaDoc](http://jd.obnoxint.net/omc-lib/net/obnoxint/mcdev/util/package-frame.html)

## Contents

* AbstractPlayer - abstract wrapper for org.bukkit.entity.Player objects
* AbstractWorld - abstract wrapper for org.bukkit.World objects
* PluginInstanceRequirementHelper - notifies a class as soon as the instance of
a specified plugin is available.
* SerializableRecipe (super class)- serializable and immutable wrappers for the
three types of recipes.
* SerializableItemStack - serializabe and immutable wrapper for
org.bukkit.inventory.ItemStack objects.
* SerializableLocation - serializable and immutable wrapper for
org.bukkit.Location objects
* SerializableNote - combines org.bukkit.Instrument and org.bukkit.Note objects
into a serializable and immutable wrapper.

### AbstractPlayer
With the AbstractPlayer class you can add your own logic to a Player object
without the necessity to extend the CraftPlayer class directly and therefore
without the necessity to add CraftBukkit to your build path as well.

Extend the AbstractPlayer class and add new fields and methods or override the
existing ones. If you are overriding the existing methods it is highly
recommended to always make a super call in the first or last line of your method
depending on the logic you are going to implement.

With the AbstractPlayer class you can avoid a shortcoming of CraftBukkit:
Usually you should never store an instance of Player in a collection (like a
HashMap) because the object may become obsolete when the player reconnects. If
you are storing instances of AbstractPlayer in a collection, call the `update()`
method as necessary and the reference will be updated automatically.

### AbstractWorld
With the AbstractWorld class you can add your own logic to a World object
without the necessity to extend the CraftWorld class directly and therefore
without the necessity to add CraftBukkit to your build path as well.

Extend the AbstractWorld class and add new fields and methods or override the
existing ones. If you are overriding the existing methods it is highly
recommended to always make a super call in the first or last line of your method
depending on the logic you are going to implement.

Call the `update()` in order to update the reference to the CraftWorld instance.
This is only necessary if the world was deleted and a new world was created with
the same name.

### PluginInstanceRequirementHelper
The PluginInstanceRequirementHelper class is useful when your plugin has a very
loose binding to the functionality of another plugin. An instance of this class
will periodically try to acquire a reference to the requested plugin and
notifies your plugin as soon as a reference exists. Read the JavaDoc for more
information about how to use this class.

### SerializableRecipe
The SerializableRecipe class is the superclass of the following classes:

* [SerializableFurnaceRecipe](http://jd.obnoxint.net/omc-lib/net/obnoxint/mcdev/util/SerializableFurnaceRecipe.html)
* [SerializableShapedRecipe](http://jd.obnoxint.net/omc-lib/net/obnoxint/mcdev/util/SerializableShapedRecipe.html)
* [SerializableShapelessRecipe](http://jd.obnoxint.net/omc-lib/net/obnoxint/mcdev/util/SerializableShapelessRecipe.html)

All subclasses of SerializableRecipe share the method getResult() which returns
a SerializableItemStack.

### SerializableItemStack
The SerializableItemStack class is an immutable wrapper for instances of
org.bukkit.inventory.ItemStack and includes support for enchantments.

The `toString()` method returns a String which can later be parsed by the static
`fromString()` method in order to recreate a SerializableItemStack. The returned
String can have two formats:

* Without enchantments: `[type],[amount],[durability],[data]`
* With enchantments: `[type],[amount],[durability],[data],{[enchantment1]=[level];[enchantment2]=[level]}`

Examples:

* A diamond pickaxe: `278,1,0,0`
* Ten torches with Knockback X enchantment: `50,10,0,0,{19=10}`

All enchantments will be added in an *unsafe* way, which allows to add
enchantments to items which normally can not be enchanted or to add enchantments
to items which usually never recieve such an enchantment (e.g. Bane of
Arthropods to Bows).

### SerializableLocation
The SerializableLocation class is an immutable wrapper for instances of
org.bukkit.Location. The world is being stored by its name.

The `toString()` method returns a String which can later be parsed by the static
`fromString()` method in order to recreate a SerializableLocation. The returned
String has the following format: `world x y z pitch yaw`

### SerializableNote
The SerializableNote class is a combined immutable wrapper for instances of
org.bukkit.Note and members of the org.bukkit.Instrument enumeration.