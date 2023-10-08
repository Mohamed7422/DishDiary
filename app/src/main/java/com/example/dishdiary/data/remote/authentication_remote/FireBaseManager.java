package com.example.dishdiary.data.remote.authentication_remote;

import androidx.annotation.NonNull;

import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.model.authDTO.UploadMealsDTO;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FireBaseManager implements FirebaseSource{

    private static FireBaseManager fireBaseManager = null;

    private FireBaseManager() {
    }
    public static synchronized FireBaseManager getInstance(){
        if (fireBaseManager == null){
            fireBaseManager = new FireBaseManager();
        }
        return fireBaseManager;
    }

    @Override
    public void logIn(AuthenticationPoJo authPojo, IFirebaseDelegate firebaseDelegate) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(authPojo.getUserEmail(),authPojo.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseDelegate.onSuccess();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseDelegate.onFailure(e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void signUp(AuthenticationPoJo authPojo, IFirebaseDelegate firebaseDelegate) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(authPojo.getUserEmail(),
                authPojo.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                           .setDisplayName(authPojo.getUserName())
                           .build();
                   user.updateProfile(profileChangeRequest);
                   firebaseDelegate.onSuccess();
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                  firebaseDelegate.onFailure(e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void uploadMealsList(String userEmail, List<MealPlanDTO> mealsPlanList, List<MealsItemDTO> mealsList, IFirebaseDelegate firebaseDelegate) {
           UploadMealsDTO uploadMealsDTO = new UploadMealsDTO(mealsPlanList,mealsList);
           //connect to firebaseStoreData
        FirebaseFirestore.getInstance().collection("MealsList").document(userEmail).set(uploadMealsDTO)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseDelegate.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      firebaseDelegate.onFailure(e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void downloadMeals(String userEmail, IFirebaseDelegate firebaseDelegate) {
        //connect to firebaseStoreData
        FirebaseFirestore.getInstance().collection("MealsList").document(userEmail)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            DocumentSnapshot result = task.getResult();
                            if (result.exists()){
                                UploadMealsDTO uploadMealsDTO = result.toObject(UploadMealsDTO.class);
                                firebaseDelegate.onDownloadSuccess(uploadMealsDTO.getMealsItemDTOList(),uploadMealsDTO.getPlanDTOList());

                            }else {
                                System.out.println("Can not found document");
                            }

                        }else {
                            firebaseDelegate.onFailure(task.toString());
                        }
                    }
                });
    }
}
