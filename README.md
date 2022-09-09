# Fitur Aplikasi #

* Simpan No KTP
* Upload scan KTP

## Tanpa Enkripsi ##

Jalankan aplikasi secara normal

```
mvn clean spring-boot:run
```

## Enkripsi Data dengan AES ##

Jalankan aplikasi menggunakan profile `aeslocal`

```
SPRING_ACTIVE_PROFILES=aeslocal mvn clean spring-boot:run
```

## Enkripsi Data dengan Vault ##

1. Jalankan Vault Server

    ```
    vault server -dev
    ```

    Outputnya seperti ini

    ```
    ==> Vault server configuration:

             Api Address: http://127.0.0.1:8200
                     Cgo: disabled
         Cluster Address: https://127.0.0.1:8201
              Go Version: go1.16.6
              Listener 1: tcp (addr: "127.0.0.1:8200", cluster address: "127.0.0.1:8201", max_request_duration: "1m30s", max_request_size: "33554432", tls: "disabled")
               Log Level: info
                   Mlock: supported: false, enabled: false
           Recovery Mode: false
                 Storage: inmem
                 Version: Vault v1.8.1
             Version Sha: 4b0264f28defc05454c31277cfa6ff63695a458d

    ==> Vault server started! Log data will stream in below:
    
    2021-08-22T16:47:27.375+0700 [INFO]  proxy environment: http_proxy="" https_proxy="" no_proxy=""
    2021-08-22T16:47:27.375+0700 [WARN]  no `api_addr` value specified in config or in VAULT_API_ADDR; falling back to detection if possible, but this value should be manually set
    2021-08-22T16:47:27.376+0700 [INFO]  core: security barrier not initialized
    2021-08-22T16:47:27.376+0700 [INFO]  core: security barrier initialized: stored=1 shares=1 threshold=1
    2021-08-22T16:47:27.376+0700 [INFO]  core: post-unseal setup starting
    2021-08-22T16:47:27.380+0700 [INFO]  core: loaded wrapping token key
    2021-08-22T16:47:27.380+0700 [INFO]  core: successfully setup plugin catalog: plugin-directory=""
    2021-08-22T16:47:27.380+0700 [INFO]  core: no mounts; adding default mount table
    2021-08-22T16:47:27.381+0700 [INFO]  core: successfully mounted backend: type=cubbyhole path=cubbyhole/
    2021-08-22T16:47:27.381+0700 [INFO]  core: successfully mounted backend: type=system path=sys/
    2021-08-22T16:47:27.381+0700 [INFO]  core: successfully mounted backend: type=identity path=identity/
    2021-08-22T16:47:27.383+0700 [INFO]  core: successfully enabled credential backend: type=token path=token/
    2021-08-22T16:47:27.383+0700 [INFO]  rollback: starting rollback manager
    2021-08-22T16:47:27.383+0700 [INFO]  core: restoring leases
    2021-08-22T16:47:27.383+0700 [INFO]  identity: entities restored
    2021-08-22T16:47:27.383+0700 [INFO]  identity: groups restored
    2021-08-22T16:47:27.383+0700 [INFO]  core: post-unseal setup complete
    2021-08-22T16:47:27.384+0700 [INFO]  expiration: lease restore complete
    2021-08-22T16:47:27.384+0700 [INFO]  core: root token generated
    2021-08-22T16:47:27.384+0700 [INFO]  core: pre-seal teardown starting
    2021-08-22T16:47:27.384+0700 [INFO]  rollback: stopping rollback manager
    2021-08-22T16:47:27.384+0700 [INFO]  core: pre-seal teardown complete
    2021-08-22T16:47:27.384+0700 [INFO]  core.cluster-listener.tcp: starting listener: listener_address=127.0.0.1:8201
    2021-08-22T16:47:27.384+0700 [INFO]  core.cluster-listener: serving cluster requests: cluster_listen_address=127.0.0.1:8201
    2021-08-22T16:47:27.384+0700 [INFO]  core: post-unseal setup starting
    2021-08-22T16:47:27.384+0700 [INFO]  core: loaded wrapping token key
    2021-08-22T16:47:27.384+0700 [INFO]  core: successfully setup plugin catalog: plugin-directory=""
    2021-08-22T16:47:27.384+0700 [INFO]  core: successfully mounted backend: type=system path=sys/
    2021-08-22T16:47:27.384+0700 [INFO]  core: successfully mounted backend: type=identity path=identity/
    2021-08-22T16:47:27.384+0700 [INFO]  core: successfully mounted backend: type=cubbyhole path=cubbyhole/
    2021-08-22T16:47:27.385+0700 [INFO]  core: successfully enabled credential backend: type=token path=token/
    2021-08-22T16:47:27.385+0700 [INFO]  rollback: starting rollback manager
    2021-08-22T16:47:27.385+0700 [INFO]  core: restoring leases
    2021-08-22T16:47:27.385+0700 [INFO]  expiration: lease restore complete
    2021-08-22T16:47:27.385+0700 [INFO]  identity: entities restored
    2021-08-22T16:47:27.385+0700 [INFO]  identity: groups restored
    2021-08-22T16:47:27.385+0700 [INFO]  core: post-unseal setup complete
    2021-08-22T16:47:27.385+0700 [INFO]  core: vault is unsealed
    2021-08-22T16:47:27.386+0700 [INFO]  core: successful mount: namespace="" path=secret/ type=kv
    2021-08-22T16:47:27.397+0700 [INFO]  secrets.kv.kv_54d0d613: collecting keys to upgrade
    2021-08-22T16:47:27.397+0700 [INFO]  secrets.kv.kv_54d0d613: done collecting keys: num_keys=1
    2021-08-22T16:47:27.397+0700 [INFO]  secrets.kv.kv_54d0d613: upgrading keys finished
    WARNING! dev mode is enabled! In this mode, Vault runs entirely in-memory
    and starts unsealed with a single unseal key. The root token is already
    authenticated to the CLI, so you can immediately begin using Vault.
    
    You may need to set the following environment variable:
    
        $ export VAULT_ADDR='http://127.0.0.1:8200'
    
    The unseal key and root token are displayed below in case you want to
    seal/unseal the Vault or re-authenticate.
    
    Unseal Key: RjzDixgmnX3WVm8W+w/RXjeqq2BIfWGvcjozY/ry/UU=
    Root Token: s.i4cnIV0dNXhUbazeIFShZam8
    
    Development mode should NOT be used in production installations!
    ```

2. Cek status vault server. Buka terminal satu lagi, kemudian set `VAULT_ADDR` dan `VAULT_TOKEN`

    ```
    export VAULT_ADDR='http://127.0.0.1:8200'
    export VAULT_TOKEN='s.i4cnIV0dNXhUbazeIFShZam8'
    ```
   
    Setelah itu, cek status

    ```
    vault status
    ```
   
    Outputnya seperti ini

    ```
    Key             Value
    ---             -----
    Seal Type       shamir
    Initialized     true
    Sealed          false
    Total Shares    1
    Threshold       1
    Version         1.8.1
    Storage Type    inmem
    Cluster Name    vault-cluster-cac24b22
    Cluster ID      0cde6468-5a95-2d6c-282f-e2d2bd52cbd3
    HA Enabled      false
    ```

3. Enable `transit` secret engine

    ```
    vault secrets enable transit
    ```
   
    Outputnya seperti ini

    ```
    Success! Enabled the transit secrets engine at: transit/
    ```
    
    Cek status

    ```
    vault secrets list
    ```
   
    Outputnya seperti ini

    ```
    Path          Type         Accessor              Description
    ----          ----         --------              -----------
    cubbyhole/    cubbyhole    cubbyhole_af98da45    per-token private secret storage
    identity/     identity     identity_a2a21ca7     identity store
    secret/       kv           kv_54d0d613           key/value secret storage
    sys/          system       system_20f0a31f       system endpoints used for control, policy and debugging
    transit/      transit      transit_39891d6a      n/a
    ```

4. Generate key enkripsi untuk file KTP

    ```
    vault write -f transit/keys/data-ktp
    ```
    
    Outputnya seperti ini 

    ```
    Success! Data written to: transit/keys/data-ktp
    ```

5. Lihat daftar key dalam secret engine `transit`

    ```
    vault list transit/keys
    ```

    Outputnya seperti ini

    ```
    Keys
    ----
    data-ktp
    ```

6. Lihat info detail key `data-ktp`

    ```
    vault read transit/keys/data-ktp
    ```

    Outputnya seperti ini

    ```
    Key                       Value
    ---                       -----
    allow_plaintext_backup    false
    deletion_allowed          false
    derived                   false
    exportable                false
    keys                      map[1:1631694403]
    latest_version            1
    min_available_version     0
    min_decryption_version    1
    min_encryption_version    0
    name                      data-ktp
    supports_decryption       true
    supports_derivation       true
    supports_encryption       true
    supports_signing          false
    type                      aes256-gcm96
    ```

7. Copy `Root Token` ke variabel `vault.token` di file `application.properties`

8. Jalankan aplikasi

    ```
    SPRING_PROFILES_ACTIVE=vault mvn clean spring-boot:run
    ```

## TODO ##

* Buat interface `SecureValue` dengan method `encrypt` dan `decrypt`
* Entity yang memiliki secure property harus implement ini
* Buat entity listener `SecureValueListener`

    * `PrePersist` : encrypt field yang rahasia
    * `PostLoad` : decrypt field yang rahasia

## Referensi ##

* https://www.bytefish.de/blog/spring_boot_multitenancy_entity_listeners.html