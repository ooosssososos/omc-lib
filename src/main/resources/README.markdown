# omc-lib
omc-lib is a development and adminstration tool for CraftBukkit and
CraftBukkit-Plugins. Its core function is the *Feature Manager*, which provides
supporting plugins an easy way to distribute their functions and interfaces
(*Features*) to other plugins and features.

omc-lib also offers various useful classes and methods for developers. For a
complete documentation of these classes take a look at the
[Javadoc](http://jd.obnoxint.net/omc-lib).

***

## The Feature Manager, Features and Feature Properties
omc-lib provides a simple mechanism to manage the already included features, as
well as features added by supporting plugins. This mechanism is provided by the
Feature Manager.

### Feature Manager
The FeatureManager class is an abstract container of Features. A Feature
Manager is not required in order to operate a Feature but makes it simple to
maintain dependencies between features.

The idea is to allow to make the function of a single feature as purposive as
possible:  
Imagine you have one Feature which controls the idling behaviour of a player and
another Feature which provides broadcasting and messaging functions, but the
latter has been deactivated by the server operator. You can still call the
corresponding method of the broadcasting-Feature without having to check the
configuration interface of your idling-Feature. The server operator will get
the expected result and you have to write less code.

omc-lib provides a subclass of the FeatureManager class which already offers
some useful options, like blacklisting of Features and automatic activation. You
are also free to implement your own subclass of FeatureManager into your plugin.

### Feature
You can imagine a Feature to be something like a mini-plugin. While a plugin
usually provides a lot of functions, interfaces and (in worst cases cross-)
dependencies, a Feature is absolutely straightforward and provides only the
functions it is designed for. Each Feature manages itself and no other Feature
should be able to control or configure the behaviour of another Feature.

All functions a certain Feature offers are always available to other Features,
but only the implementing Feature can decide when or if to take action. This is
done by checking the active state every time (e.g) a method is being invoked or
a Listener is being triggered by an event. This can be accomplished by calling
the method *isFeatureActive* which all Features must implement.

When a Feature is active it should for example:
* load its properties,
* register its Listeners,
* allow its CommandExecutors to process commands,
* allow methods to proceed.

When a Feature is inactive it should for example:
* store its properties,
* unregister its Listeners,
* cancel its CommandExecutors,
* make methods to return immediately.

### Feature Properties
The FeatureProperties class helps you to write the configuration interface for
your Features by providing the usual boilerplate-code. It uses the
[java.util.Properties]
(http://docs.oracle.com/javase/7/docs/api/java/util/Properties.html) class. All
you have to do is to add the necessary methods for getting and setting the
individual properties and call the loadProperties() or storeProperties() method.

Each Feature has exactly one instance of the FeatureProperties class, which is
used to control the behaviour of the Feature as intended by the server operator.

If you prefer to implement additional fields into your subclass of
FeatureProperties, you should take advantage of the methods onFileCreated(),
onLoad(), onLoaded(), onStore() and onStored() by overriding them.

***

## Implemented Features

### Metrics
The omc-lib Metrics Feature is an interface for Hidendras Metrics. This feature
is intended to

1. give server operators more control over the Metrics tracking behaviour of
installed plugins and
2. provide developers who want to use Hidendras Metrics with a simple, reliable
and extendable API in order to reduce boilerplate-code, making their code easier
to maintain and thereby allowing them to focus on the important things.

#### Why should I use the omc-lib Metrics Feature?
If you are a developer the reasons should become obvious as soon as you are
familiar with the service the Metrics Feature offers you. If you are a server
owner, this is a very legitimate question!

By allowing plugins to collect usage data you are doing a great favor to the
developers who made them for you:

With the collected data, plugin developers are able to determine which features
are used the most or the least and are thereby able to know which part of their
code requires the most attention for future features and functions. Plugin
developers want to satisfy you and your players, giving your players the best
and most entertaining Minecraft-Multiplayer experience possible. The decisions
they make depend on your feedback and the collected usage data.  
By keeping the omc-lib Metrics Feature enabled, which contains an adapter to
Hidendras Metrics, all you have to do is enjoying the game!

There are a lot of benefits in using MetricsFramework. What they are in detail
depends on the perspective you are looking at it from.

*Benefits for server operators:*
* Allow or disallow single plugins to send data.
* Know exactly what data was sent.
* Control reporting behaviour and interval.

*Benefits for plugin developers:*
* Let omc-lib automatically persist and restore the data of your plotters.
* No need to implement Hidendras Metrics class in each of your plugins.
* Increase the trust in your plugin by giving your users more control without
additional coding effort.

### Compendium
The omc-lib Compendium Feature is an intuitive and easy to use provider for
in-game manuals.

The compendium consists of multiple Topics which again consist of multiple
chapters. Both, topics and chapters, can contain a permission requirement to
read them. Chapters can contain keywords and references, which are automatically
indexed by the Compendium Feature. E.g. if an user looks for the keyword "spawn"
they can immediately see what chapters contain this keyword.

***

## Useful Utilities
omc-lib offers a bunch of useful utilities. They are located in the packages
net.obnoxint.util and net.obnoxint.mcdev.util.

### UID-Provider
The UID-Provider is available via the plugin instance. It automatically manages
and persists an unlimited number of
[UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier)s per
Feature.  
It's, for example, being used by the Metrics Feature in order to allow
mcstats.org the identification of a participating server.

### Player Properties Manager
The integrated Player Properties Manager lets you store properties for each
player, either as global properties or as properties maintained by a feature
Feature. It automatically stores the data to an individual file per player and
loads it only if needed.

Getting and setting PlayerProperties is very similar to getting and setting
properties of an org.bukkit.configuration.MemorySection.

### Serializable and abstract wrappers of Minecraft objects
A common weakness of CraftBukkits API is the lack of serializable objects, which
often leads to the necessity of writing redundant code. The serializable wrapper
classes of the net.obnoxint.mcdev.util package are very useful when plugin
authors need to (de-)serialize usually not serializable objects.

The same package also provides abstract wrappers of usually not extendible
classes, like the Player class.

### Other useful utilities

*net.obnoxint.util.DelayMeasure*

> The DelayMeasure class provides the ability to measure delays between two
points of program execution. Once ended, an instance of the class can not be
used to make another measurement. The class implements the Serializable
interface and can therefore be used across runtimes and platforms.

*net.obnoxint.util.Stat*

> The Stat class is meant for statistical applications. It is abstract and
should be extended. The class also contains the static class *SimpleStat* which
already extends Stat and is not abstract, for usecases which require only the
basic functionality of the Stat class. Stat also contains the static interface
*StatListener*, which can be attached to an instance in order to get notified
about balance modifications.

*net.obnoxint.util.VersionNumber*

> The VersionNumber class provides a standardized object implementation for
version numbers following the format "major.minor.revision.build" with an
optional mark-up. The output of the toString() method of an instance of
VersionNumber could look like this: "1.5.0.200-RC2"  
VersionNumber includes the interfaces Serializable and Comparable and is perfect
for checking for version differences and defining dependencies.