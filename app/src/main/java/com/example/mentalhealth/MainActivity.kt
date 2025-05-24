class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MentalHealthRepository(
            moodEntryDao = database.moodEntryDao(),
            userDao = database.userDao()
        )

        setContent {
            MentalHealthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MentalHealthViewModel = viewModel(
                        factory = MentalHealthViewModelFactory(repository)
                    )
                    val navController = rememberNavController()
                    MainScreen(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
} 