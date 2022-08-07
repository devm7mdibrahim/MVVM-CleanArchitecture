package com.devm7mdibrahim.utils.extensions

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.findNavController

fun View.navigate(direction: NavDirections) {
    catch {
        findNavController().navigate(direction)
    }
}

fun View.navigate(@IdRes destination: Int, args: Bundle? = null) {
    catch {
        findNavController()
            .navigate(destination, args)
    }
}

fun Fragment.navigate(@IdRes destination: Int, args: Bundle? = null) {
    catch {
        findNavController().navigate(destination, args)
    }
}

fun Fragment.navigate(direction: NavDirections, options: NavOptions? = null) {
    catch {
        findNavController().navigate(direction, options)
    }
}

fun Fragment.navigate(direction: NavDirections, extras: Navigator.Extras) {
    catch {
        findNavController().navigate(direction, extras)
    }
}

fun Fragment.back() {
    catch {
        findNavController().popBackStack()
    }
}