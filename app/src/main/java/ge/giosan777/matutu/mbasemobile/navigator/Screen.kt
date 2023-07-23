package ge.giosan777.matutu.mbasemobile.navigator

sealed class Screen(val route:String){
    object MBase:Screen(route = "mbase_screen")
    object MBaseOrg:Screen(route = "mbase_org_screen")
    object WelcomeScreen:Screen(route = "welcome_Screen")

}
