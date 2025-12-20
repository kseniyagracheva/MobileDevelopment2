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
