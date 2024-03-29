package ge.giosan777.matutu.mbasemobile.navigator

sealed class Screen(val route:String){
    object MBase:Screen(route = "mbase_screen")
    object MBaseOrg:Screen(route = "mbase_org_screen")
    object WelcomeScreen:Screen(route = "welcome_Screen")
    object AddCompanyCard:Screen(route = "AddCompany_Screen")
    object VideoScreenCallIdentify:Screen(route = "video_Screen_Call_Identify")
    object VideoScreenXiaomi:Screen(route = "video_Screen_Xiaomi")

}
