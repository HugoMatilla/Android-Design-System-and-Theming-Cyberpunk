package com.hugomatilla.androidthemingcyberpunk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.SupportMenuInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

  private lateinit var homeFragment: HomeFragment
  private lateinit var widgetsFragment: WidgetsFragment
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    setTheme(R.style.Theme_AndroidThemingKitty)
    setContentView(R.layout.activity_main)
    setupFragments()
    setupBottomNavigation()
    setupToolbar()
  }

  private fun setupFragments() {
    homeFragment = HomeFragment()
    widgetsFragment = WidgetsFragment()

    supportFragmentManager.commit {
      add(R.id.fragmentContainer, widgetsFragment, null)
      add(R.id.fragmentContainer, homeFragment, null)
    }

    homeFragment.show()
  }

  private fun setupBottomNavigation() {
    bottomNavigation.setOnNavigationItemSelectedListener {
      showFragmentByMenuItemId(it.itemId)
      true
    }
  }

  private fun showFragmentByMenuItemId(itemId: Int) {
    when (itemId) {
      R.id.home -> homeFragment.show()
      R.id.widgets -> widgetsFragment.show()
    }
  }

  private fun Fragment.show() {
    val fragment = this
    supportFragmentManager.commit { replace(R.id.fragmentContainer, fragment, null) }
  }

  override fun onSaveInstanceState(savedInstanceState: Bundle) {
    super.onSaveInstanceState(savedInstanceState)
    savedInstanceState.putInt(LAST_SELECTED_ITEM, bottomNavigation.selectedItemId)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    val itemId = savedInstanceState.getInt(LAST_SELECTED_ITEM, R.id.home)
    showFragmentByMenuItemId(itemId)
    bottomNavigation.selectedItemId = itemId
  }

  @SuppressLint("RestrictedApi")
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater = SupportMenuInflater(this)
    inflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.darkToggle -> {
      val goDark = AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES
      AppCompatDelegate.setDefaultNightMode(if (goDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
      true
    }
    else -> {
      super.onOptionsItemSelected(item)
    }
  }

  private fun setupToolbar() {
    toolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.darkToggle -> {
          val goDark = AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES
          AppCompatDelegate.setDefaultNightMode(if (goDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
      }
      true
    }
  }

  companion object {
    private const val LAST_SELECTED_ITEM = "LAST_SELECTED_ITEM"
  }
}