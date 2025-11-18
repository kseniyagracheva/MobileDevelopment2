**Отчет по практической работе №3**
---
*MovieProject*

Сначала необходимо было раздлеить логику в MainActivity на 2 файла - один файл для взаимодействия с логикой, воторой файл - только взаимодействие с интерфейсом пользователя.
Был создан новый файл MainViewModel. При создании ViewModel в файле MainActivity было произведено логирование. Результат логирования:

<img width="1239" height="54" alt="image" src="https://github.com/user-attachments/assets/6b0f8fe9-b662-4c42-bc78-9cc925ded6cc" />

Затем была создана ViewModelFactory, в которой были реализованы все зависимости, которые нужны для взаимодействия с бизнес логикой. Это необходимо было сделать в отдельном файле, т.к. ViewModel должна быть изолированна. Во ViewModelFactory был создан экземпляр интерфейса, куда был передан контекст. А затем этот репозитория был при создании ViewModel передан ViewModel.

Затем был релизован паттер Observe, а точнее - LiveData. Была созданная новая переменная - типа MutableLiveData<>(). В MainActivity была реализована подписка на изменения переменной. То есть когда переменная изменится, в текстовом поле установится текст для каждого подписанного Activity (при повороте экрана Activity пересоздается).
Таким образом получился вот такой результат:

<img width="382" height="841" alt="image" src="https://github.com/user-attachments/assets/64021cd9-5e0c-4f90-b6d1-cbd0aad95a2b" />
<img width="377" height="838" alt="image" src="https://github.com/user-attachments/assets/0e72889b-cc7f-41bc-9736-b928220ecd9f" />
<img width="953" height="438" alt="image" src="https://github.com/user-attachments/assets/4eea5502-a2f4-46b7-94cf-90f156882615" />
