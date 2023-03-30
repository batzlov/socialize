package com.example.socialize.util

import android.os.Environment
import android.util.Log
import java.io.*

class LocalStorage {
    private val fileName: String = "localStorage.txt"
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), this.fileName)

    fun save(content: String) {
        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(this.file)
            // Write to the file
            fos.write(content.toByteArray())
            // Close the stream
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun load(): String {
        val fileName: String = "localStorage.txt"
        var fileContent: String = ""

        var fr: FileReader? = null
        val externalFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        val stringBuilder = StringBuilder()
        try {
            fr = FileReader(externalFile)

            val br = BufferedReader(fr)
            var line = br.readLine()
            while (line != null) {
                stringBuilder.append(line).append('\n')
                line = br.readLine()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileContent = "$stringBuilder"
        }

        return fileContent
    }

    fun clear() {
        var fos: FileOutputStream? = null

        try {
            fos = FileOutputStream(this.file)

            fos.write("".toByteArray())

            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun saveValue(key: String, value: String): Boolean {
        var fos: FileOutputStream? = null
        var fileContent: String = this.load().trim()
        var success = false

        // add key and value, save file
        try {
            if(fileContent.isNotEmpty()) {
                fileContent = "$fileContent;$key=$value";

                val keyValuePairs = fileContent.splitToSequence(";")
                var updatedKeyValuePairs = ""
                for((index, item) in keyValuePairs.withIndex()) {
                    val k: String = item.splitToSequence("=").elementAt(0)
                    var v: String = item.splitToSequence("=").elementAt(1)

                    if(k == key) {
                        v = value
                    }

                    if(updatedKeyValuePairs == "") {
                        updatedKeyValuePairs += "$k=$v"
                    } else {
                        updatedKeyValuePairs += ";$k=$v"
                    }
                }
            } else {
                fileContent = "$key=$value";
            }


            fos = FileOutputStream(this.file)

            fos.write(fileContent.toByteArray())

            fos.close()

            success = true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.d("SAVE VALUE -> ", fileContent)

        return success
    }

    fun getValue(key: String): String {
        val fileContent: String = this.load()
        var value: String = ""

        if(fileContent.isNotEmpty()) {
            val keyValuePairs = fileContent.splitToSequence(";")
            for(item in keyValuePairs) {
                val k: String = item.splitToSequence("=").elementAt(0)
                val v: String = item.splitToSequence("=").elementAt(1)

                if(k == key) {
                    value = v.trim();
                }
            }
        }

        Log.d("GET VALUE KEY -> ", key)
        Log.d("GET VALUE -> ", value)
        return value;
    }
}