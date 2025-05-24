@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MentalHealthViewModel
) {
    val items = listOf(
        BottomNavItem.Chatbot,
        BottomNavItem.Mood,
        BottomNavItem.Profile
    )
    
    val mainNavController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            mainNavController.navigate(item.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = BottomNavItem.Chatbot.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Chatbot.route) {
                ChatbotScreen()
            }
            composable(BottomNavItem.Mood.route) {
                MoodTrackerScreen(viewModel = viewModel)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
} 