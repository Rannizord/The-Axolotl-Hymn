package me.mrgaabriel.axolotlhymn.utils

import java.awt.*
import java.io.File
import java.util.ArrayList
import java.io.IOException

object HymnUtils {

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Throws(ClassNotFoundException::class, IOException::class)
    fun getClasses(packageName: String): Array<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader!!
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path)
        val dirs = ArrayList<File>()
        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            dirs.add(File(resource.file))
        }
        val classes = ArrayList<Class<*>>()
        for (directory in dirs) {
            classes.addAll(findClasses(directory, packageName))
        }
        return classes.toTypedArray()
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @Throws(ClassNotFoundException::class)
    fun findClasses(directory: File, packageName: String): List<Class<*>> {
        val classes = ArrayList<Class<*>>()
        if (!directory.exists()) {
            return classes
        }
        val files = directory.listFiles()
        for (file in files!!) {
            if (file.isDirectory) {
                assert(!file.name.contains("."))
                classes.addAll(findClasses(file, packageName + "." + file.name))
            } else if (file.name.endsWith(".class")) {
                classes.add(Class.forName(packageName + '.'.toString() + file.name.substring(0, file.name.length - 6)))
            }
        }
        return classes
    }

    fun hexToColor(colorStr: String): Color? {
        try {
            val r = Integer.valueOf(colorStr.substring(1, 3), 16)
            val g = Integer.valueOf(colorStr.substring(3, 5), 16)
            val b = Integer.valueOf(colorStr.substring(5, 7), 16)

            return Color(r, g, b)
        } catch (e: NumberFormatException) {
            return null
        } catch (e: StringIndexOutOfBoundsException) {
            return null
        }
    }
}