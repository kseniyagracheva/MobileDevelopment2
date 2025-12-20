**Отчет по практике №6**
-----
В данной практической работе была произведена работа с фрагментами.

*fragmentApp*
-----

Сначала был создан класс фрагмента NumFragment, который был унаследован от Fragment. В данном классе реализуется логика занесения номера по списку в текстовое поле

    public class NumFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d(NumFragment.class.getSimpleName(), "onCreateView");
            View view   =  inflater.inflate(R.layout.fragment_num,  container, false);
            return  view;
        }
    
        @Override
        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            int numberStudent = requireArguments().getInt("my_number_student", 0);
            Log.d(NumFragment.class.getSimpleName(), String.valueOf(numberStudent));
            TextView textView = view.findViewById(R.id.textNumber);
            textView.setText("Номер по списку: " + numberStudent);
        }
    }

Далее в классе MainActivity, в котором создается новый Bundle и после фрагмент добавляется в основной LayOut

    public class MainActivity extends AppCompatActivity {
    
        @Override protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            if (savedInstanceState == null) {
                Bundle args = new Bundle();
                args.putInt("my_number_student", 8);
    
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.main, NumFragment.class, args)
                        .commit();
            }
        }
    }

<img width="360" height="802" alt="image" src="https://github.com/user-attachments/assets/9a23e221-7dde-46bf-9da6-e2d7eb4ff952" />

*FragmentManagerApp*
-----

Затем был создан модуль для реализации связи между фрагментами. Между двумя фрагментами реализуется связь при помощи SharedViewModel.

    public class SharedViewModel extends ViewModel {
        private final MutableLiveData<Country> selectedCountry = new MutableLiveData<>();
    
        public void selectCountry(Country country) {
            selectedCountry.setValue(country);
        }
    
        public LiveData<Country> getSelectedCountry() {
            return selectedCountry;
        }
    }

Затем в основном фрагменте создается список стран и подробная информация по ним. Также переключение на другой фрагмент при помощи replace.

      public class HeaderFragment extends Fragment {
    
        private SharedViewModel viewModel;
        private List<Country> countries;
    
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        }
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_header, container, false);
        }
    
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
    
            countries = new ArrayList<>();
            countries.add(new Country("Венгрия", "Будапешт", 9603634 ));
            countries.add(new Country("Германия", "Берлин", 83491249));
            countries.add(new Country("Испания", "Мадрид", 49315949));
            countries.add(new Country("Россия", "Москва", 146119928));
            countries.add(new Country("Португалия", "Лиссабон", 10467366));
            countries.add(new Country("Франция", "Париж", 67421162));
    
            ListView listView = view.findViewById(R.id.listView);
    
            ArrayAdapter<Country> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    countries
            );
            listView.setAdapter(adapter);
    
            listView.setOnItemClickListener((parent, itemView, position, id) -> {
                Country selectedMovie = countries.get(position);
    
                viewModel.selectCountry(selectedMovie);
    
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, DetailsFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

Затем во фрагменте DetailFragment была добавлена логика связи между фрагментами и вывода подробной информации по стране:

    public class DetailsFragment extends Fragment {
    
        private SharedViewModel viewModel;
        private TextView nameTextView;
        private TextView capitalTextView;
    
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        }
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_details, container, false);
        }
    
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
    
            nameTextView = view.findViewById(R.id.nameTextView);
            capitalTextView = view.findViewById(R.id.capitalTextView);
    
            viewModel.getSelectedCountry().observe(getViewLifecycleOwner(), country -> {
                if (country != null) {
                    nameTextView.setText(country.getName());
                    capitalTextView.setText("Столица: " + country.getCapital());
                }
            });
        }
    }

Далее был еще создан класс самой страны.

Результат:
<img width="311" height="678" alt="image" src="https://github.com/user-attachments/assets/14b74f09-137d-4b5a-95a0-1c9a1702d6f8" />
<img width="310" height="666" alt="image" src="https://github.com/user-attachments/assets/3999d72f-885b-4b1a-8795-408c415a2a7c" />

*ResultApiFragmentApp*
------

Далее был создан новый модуль для отработки отправки данных из одного фрагмента в другой.
Сначала был создан фрагмент с данными, который получает данные из поля ввода и кнопки, а затем готовит их для отправки:

    public class DataFragment extends Fragment {
    
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_data, container, false);
        }
    
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
    
            EditText editText = view.findViewById(R.id.editTextData);
            Button button = view.findViewById(R.id.buttonOpenSheet);
    
            button.setOnClickListener(v -> {
                String dataToSend = editText.getText().toString();
    
                Bundle result = new Bundle();
                result.putString(BottomFragment.BUNDLE_KEY, dataToSend);
    
                BottomFragment bottomSheet = new BottomFragment();
                bottomSheet.show(getChildFragmentManager(), "MyBottomSheetFragment");
    
                getChildFragmentManager().setFragmentResult(BottomFragment.REQUEST_KEY, result);
            });
        }
    }

Затем класс, который отображает эти данные в другом фрагменте при помощи ключа запросы и Bundle:

    public class BottomFragment extends BottomSheetDialogFragment {
    
        public static final String REQUEST_KEY = "data_request_key";
        public static final String BUNDLE_KEY = "data_bundle_key";
        private TextView textViewResult;
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        }
    
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            textViewResult = view.findViewById(R.id.textViewResult);
    
            getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, (requestKey, result) -> {
                String data = result.getString(BUNDLE_KEY);
                textViewResult.setText(data);
            });
        }
    }

Результат:


<img width="320" height="709" alt="image" src="https://github.com/user-attachments/assets/0295abb3-8106-4cd5-a6df-3d9ec2b39f41" />

*RingStore*
------

Для каждого экрана, который используется в приложении был создан свой фрагмент. В данном приложении пристутсвует:
AuthFragment - фрагмент для аутентификации
RegisterFragment - фрагмент для регистрации
UserInfoFragment - фрагмент профиля
RingListFragment - фрагмент со списком колец
MetalPriceFragment - фрагмент со списком драгоценных металлов

Для реализации фрагментной навигации в приложении был создан файл nav_graph.xml. В нем указывается начальный фрагмент, с которого запускается приложение. В данном случае - это фрагмент authFragment.
Также для каждого фрагмента здесь указывается action, который вызывается при нажатии на кнопку. Данный action нужен для перехода из одного фрагмента в другой. При этом учитывается бэк-стек.

    <navigation
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/nav_graph"
        app:startDestination="@id/authFragment">
    
        <fragment
            android:id="@+id/authFragment"
            android:name="ru.mirea.gracheva.ringstore.presentation.AuthFragment"
            android:label="Auth">
            <action
                android:id="@+id/action_authFragment_to_registerFragment"
                app:destination="@id/registerFragment" />
            <action
                android:id="@+id/action_authFragment_to_userInfoFragment"
                app:destination="@id/userInfoFragment" />
        </fragment>
    
    
        <fragment
            android:id="@+id/registerFragment"
            android:name="ru.mirea.gracheva.ringstore.presentation.RegisterFragment"
            android:label="Register">
            <action
                android:id="@+id/action_registerFragment_to_authFragment"
                app:destination="@id/authFragment" />
        </fragment>
    
        <fragment
            android:id="@+id/userInfoFragment"
            android:name="ru.mirea.gracheva.ringstore.presentation.UserInfoFragment"
            android:label="UserInfo">
            <action
                android:id="@+id/action_userInfoFragment_to_metalPriceFragment"
                app:destination="@id/metalPriceFragment" />
            <action
                android:id="@+id/action_userInfoFragment_to_authFragment"
                app:destination="@id/authFragment" />
            <action
                android:id="@+id/action_userInfoFragment_to_ringListFragment"
                app:destination="@id/ringListFragment" />
        </fragment>
    
        <fragment
            android:id="@+id/metalPriceFragment"
            android:name="ru.mirea.gracheva.ringstore.presentation.MetalPriceFragment"
            android:label="MetalPrice">
            <action
                android:id="@+id/action_metalPriceFragment_to_userInfoFragment"
                app:destination="@id/userInfoFragment" />
        </fragment>
    
        <fragment
            android:id="@+id/ringListFragment"
            android:name="ru.mirea.gracheva.ringstore.presentation.RingListFragment"
            android:label="RingList">
            <action
                android:id="@+id/action_ringListFragment_to_userInfoFragment"
                app:destination="@id/userInfoFragment" />
        </fragment>
    
    </navigation>

В каждом фрагменте есть кнопки для перехода из одного фрагмента в другой. Выглядят они примерно так:

    binding.goToMetalsButton.setOnClickListener(v -> {
                navController.navigate(R.id.action_userInfoFragment_to_metalPriceFragment);
            });
    
            binding.goToRingsButton.setOnClickListener(v -> {
                navController.navigate(R.id.action_userInfoFragment_to_ringListFragment);
            });
    
            vm.ifSuccess().observe(getViewLifecycleOwner(), success ->{
                if (success){
                    Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_userInfoFragment_to_authFragment);
                }
            });
Здесь представлены 2 варианта. Один вариант обычный, второй работает при использовании во ViewModel LiveData.

В результате структура приложения выглядит вот так:

<img width="379" height="831" alt="image" src="https://github.com/user-attachments/assets/e5800595-a615-42d4-830a-8c03369a406f" />
<img width="355" height="799" alt="image" src="https://github.com/user-attachments/assets/4bbd3124-a097-4e70-97e5-e8a5571bfd1b" />
<img width="361" height="796" alt="image" src="https://github.com/user-attachments/assets/0901c569-975f-4cf0-afbd-9ee4f23d09ec" />
<img width="356" height="793" alt="image" src="https://github.com/user-attachments/assets/f69cc035-3de2-442e-b105-f6fe1862c240" />
<img width="359" height="792" alt="image" src="https://github.com/user-attachments/assets/10a3fa5a-46c0-40a4-a9e1-992b7252f238" />
<img width="360" height="796" alt="image" src="https://github.com/user-attachments/assets/9c14fc3f-42ce-4cde-9e12-7911b5bd81c3" />
<img width="356" height="783" alt="image" src="https://github.com/user-attachments/assets/b651a19b-4549-44ea-8e50-b7de2ade9944" />





