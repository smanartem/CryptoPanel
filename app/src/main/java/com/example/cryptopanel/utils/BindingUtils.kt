package com.example.cryptopanel.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SimpleArrayMap
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.reflect.KClass

object BindingUtils {
    private val inflateThreeParamsMap = SimpleArrayMap<KClass<out ViewBinding>, Method>()
    private val inflateTwoParamsMap = SimpleArrayMap<KClass<out ViewBinding>, Method>()

    inline fun <reified V : ViewBinding> getBinding(
        context: Context,
        container: ViewGroup?,
        attachToParent: Boolean = container != null,
    ): V = getBinding(V::class, LayoutInflater.from(context), container, attachToParent)

    inline fun <reified V : ViewBinding> getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = container != null,
    ): V = getBinding(V::class, inflater, container, attachToParent)

    fun <V : ViewBinding> getBinding(
        clazz: KClass<out ViewBinding>,
        context: Context,
        container: ViewGroup?,
        attachToParent: Boolean = container != null,
    ): V = getBinding(clazz, LayoutInflater.from(context), container, attachToParent)

    fun <V : ViewBinding> getBinding(
        clazz: KClass<out ViewBinding>,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = container != null,
    ): V {
        try {
            val declaredMethod = inflateThreeParamsMap.get(clazz) ?: clazz.java.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            ).also {
                inflateThreeParamsMap.put(
                    clazz,
                    it,
                )
            }
            @Suppress("UNCHECKED_CAST")
            return declaredMethod.invoke(null, inflater, container, attachToParent) as V
        } catch (e: NoSuchMethodException) {
            return getBindingForMerge(
                clazz = clazz,
                inflater = inflater,
                container = container ?: throw IllegalArgumentException("Container must not be null for merge layout")
            )
        }
    }

    private fun <V : ViewBinding> getBindingForMerge(
        clazz: KClass<out ViewBinding>,
        inflater: LayoutInflater,
        container: ViewGroup,
    ): V {
        val declaredMethod = inflateTwoParamsMap.get(clazz) ?: clazz.java.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java
        ).also {
            inflateTwoParamsMap.put(
                clazz,
                it,
            )
        }
        @Suppress("UNCHECKED_CAST")
        return declaredMethod.invoke(null, inflater, container) as V
    }
}
