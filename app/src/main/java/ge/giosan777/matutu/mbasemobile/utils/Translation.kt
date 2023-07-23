package ge.giosan777.matutu.mbasemobile.utils

fun textTraslation(categoryText: String): String {
    categoryText.trim()
    var returnedText =""
    when (categoryText) {
        "ბანკი" -> { returnedText = "Bank"  }
        "ტაქსი" -> { returnedText = "Taxi"  }
        "პიცა" -> { returnedText = "Pizza"  }
        "შაურმა" -> { returnedText = "Shawarma"  }
        "ტოტალიზატორი" -> { returnedText = "Bookmaker"  }
        "რესტორანი" -> { returnedText = "Restaurant"  }
        "დაზღვევა" -> { returnedText = "Insurance"  }
        "ონლაინ სესხი" -> { returnedText = "Online loan"  }
        "უძრავი ქონება" -> { returnedText = "Real estate"  }
        "აფთიაქი" -> { returnedText = "Pharmacy"  }
        "ტექნიკის მაღაზია" -> { returnedText = "Hardware store"  }
        "სასტუმრო" -> { returnedText = "Hotel"  }
        "სილამაზის სალონი" -> { returnedText = "Beauty salon"  }
        "სადღესასწაულო ცენტრი" -> { returnedText = "Holiday center"  }
        "ტურისტული კომპანია" -> { returnedText = "Travel company"  }
        "ინტერნეტ პროვაიდერი" -> { returnedText = "Internet provider"  }
        "არასამთავრობო ორგანიზაცია" -> { returnedText = "NGO"  }
        "კლინიკა" -> { returnedText = "Clinic"  }
        "სახელმწიფო დაწესებულება" -> { returnedText = "State institution"  }
        "მიკროსაფინანსო ორგანიზაცია" -> { returnedText = "Microfinance organization"  }
        "მშენებლობა, რემონტი" -> { returnedText = "Construction, repair"  }
        "ამანათი" -> { returnedText = "Parcel"  }
        "კომუნალური" -> { returnedText = "communal"  }
        "ავტომობილების გაქირავება" -> { returnedText = "Car rental"  }
        "სკოლა" -> { returnedText = "School"  }
        "სხვა" -> { returnedText = "Other"  }
        "კომპიუტერული მომსახურება" -> { returnedText = "Computer services"  }
        "ძიძა" -> { returnedText = "Babysitter"  }
        "დამლაგებელი" -> { returnedText = "Maid"  }
        "ფლაერების დარიგება" -> { returnedText = "Distribution of flyers"  }
        "ოფისმენეჯერი" -> { returnedText = "Office manager"  }
        "მასაჟისტი" -> { returnedText = "Masseuse"  }
        "მძღოლი" -> { returnedText = "Driver"  }
        "დიასახლისი, მზარეული" -> { returnedText = "Housewife, Cook"  }
        "ოჯახის ექიმი" -> { returnedText = "Family doctor"  }
        "რეპეტიტორი" -> { returnedText = "Tutor"  }
        "უღალტერი" -> { returnedText = "Accountant"  }
        "მოლარე, კონსულტანტი" -> { returnedText = "Cashier, consultant"  }
        "გათბობა, კონდიცირება" -> { returnedText = "Heating, air conditioning"  }
        "დაცვის თანამშრომელი" -> { returnedText = "Security guard"  }
        "იურისტი, ადვოკატი" -> { returnedText = "Lawyer"  }
        "თარჯიმანი" -> { returnedText = "Translator"  }
        "ტვირთის გადაზიდვა" -> { returnedText = "Cargo transportation"  }
        "ევაკუატორი, ტექდახმარება" -> { returnedText = "Evacuate, technical assistance"  }
        else -> {returnedText=categoryText}
    }
    return returnedText
}


// "ბანკი                      " -> { returnedText = "Bank                          "  }
// "ტაქსი                      " -> { returnedText = "Taxi                          "  }
// "პიცა                       " -> { returnedText = "Pizza                         "  }
// "შაურმა                     " -> { returnedText = "Shawarma                      "  }
// "ტოტალიზატორი               " -> { returnedText = "Bookmaker                     "  }
// "რესტორანი                  " -> { returnedText = "Restaurant                    "  }
// "დაზღვევა                   " -> { returnedText = "Insurance                     "  }
// "ონლაინ სესხი               " -> { returnedText = "Online loan                   "  }
// "უძრავი ქონება              " -> { returnedText = "Real estate                   "  }
// "აფთიაქი                    " -> { returnedText = "Pharmacy                      "  }
// "ტექნიკის მაღაზია           " -> { returnedText = "Hardware store                "  }
// "სასტუმრო</string           " -> { returnedText = "Hotel                         "  }
// "სილამაზის სალონი           " -> { returnedText = "Beauty salon                  "  }
// "სადღესასწაულო ცენტრი       " -> { returnedText = "Holiday center                "  }
// "ტურისტული კომპანია         " -> { returnedText = "Travel company                "  }
// "ინტერნეტ პროვაიდერი        " -> { returnedText = "Internet provider             "  }
// "არასამთავრობო ორგანიზაცია  " -> { returnedText = "NGO                           "  }
// "კლინიკა                    " -> { returnedText = "Clinic                        "  }
// "სახელმწიფო დაწესებულება    " -> { returnedText = "State institution             "  }
// "მიკროსაფინანსო ორგანიზაცია " -> { returnedText = "Microfinance organization     "  }
// "მშენებლობა, რემონტი        " -> { returnedText = "Construction, repair          "  }
// "ამანათი                    " -> { returnedText = "Parcel                        "  }
// "კომუნალური                 " -> { returnedText = "communal                      "  }
// "ავტომობილების გაქირავება   " -> { returnedText = "Car rental                    "  }
// "სკოლა                      " -> { returnedText = "School                        "  }
// "სხვა                       " -> { returnedText = "Other                         "  }
// "კომპიუტერული მომსახურება   " -> { returnedText = "Computer services             "  }
// "ძიძა                       " -> { returnedText = "Babysitter                    "  }
// "დამლაგებელი                " -> { returnedText = "Maid                          "  }
// "ფლაერების დარიგება         " -> { returnedText = "Distribution of flyers        "  }
// "ოფისმენეჯერი               " -> { returnedText = "Office manager                "  }
// "მასაჟისტი                  " -> { returnedText = "Masseuse                      "  }
// "მძღოლი                     " -> { returnedText = "Driver                        "  }
// "დიასახლისი, მზარეული       " -> { returnedText = "Housewife, Cook               "  }
// "ოჯახის ექიმი               " -> { returnedText = "Family doctor                 "  }
// "რეპეტიტორი                 " -> { returnedText = "Tutor                         "  }
// "უღალტერი                   " -> { returnedText = "Accountant                    "  }
// "მოლარე, კონსულტანტი        " -> { returnedText = "Cashier, consultant           "  }
// "გათბობა, კონდიცირება       " -> { returnedText = "Heating, air conditioning     "  }
// "დაცვის თანამშრომელი        " -> { returnedText = "Security guard                "  }
// "იურისტი, ადვოკატი          " -> { returnedText = "Lawyer                        "  }
// "თარჯიმანი                  " -> { returnedText = "Translator                    "  }
// "ტვირთის გადაზიდვა          " -> { returnedText = "Cargo transportation          "  }
// "ევაკუატორი, ტექდახმარება   " -> { returnedText = "Evacuate, technical assistance"  }





























